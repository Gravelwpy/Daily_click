package com.restful.daily_click.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.repository.WifiRepository;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Math.pow;


@Service
public class WifiService {
    @Autowired
    WifiRepository wifiRepository;

    public ArrayList<JSONObject> getWifiList(JSONArray wifi_rec_id_list){
        ArrayList<JSONObject> ret_data = new ArrayList<>();
        for(int i = 0;i<wifi_rec_id_list.size();i++){
            JSONObject temp = new JSONObject();
            int wifi_rec_id = wifi_rec_id_list.getInteger(i);
            temp.put("wifi_info_list",wifiRepository.getWifiList(wifi_rec_id));
            temp.put("wifi_rec_id",wifi_rec_id);
            ret_data.add(temp);
        }
        return ret_data;
    }

    //----计算两地经纬度距离-------
    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid)
    {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
        return geoCurve.getEllipsoidalDistance();
    }

    // NN 算法获取经纬度
    /*
     * wifi_test_list
     * num 求几组平均
     * average 0：平均 不等于0：加权
     * type 1 android 2 安卓小程序 3 苹果小程序
     */
    public JsonResult getNNResult(JSONArray wifi_test_list, int num, int type, int average) {
        ArrayList<JSONObject> ret_data = new ArrayList<>();
        GlobalCoordinates source = new GlobalCoordinates(29.823919, 121.578477);// 测试需要，与定位算法无关
        if( wifi_test_list != null ) {
            HashMap<String, Integer> map = new HashMap<>();
            Iterator<Object> it = wifi_test_list.iterator();
            while( it.hasNext() ) {  //将wifi信息一个个取出
                JSONObject jo = (JSONObject) it.next();
                ArrayList<JSONObject> list = null;
                if( type == 1 ) { //安卓端
                    list = wifiRepository.getResultByBSSIDSecond(jo.getString("level"), jo.getString("BSSID"), 1);
                } else if( type == 2 ){ //安卓小程序
                    list = wifiRepository.getResultByBSSIDSecond(jo.getString("signalStrength"), jo.getString("BSSID"), 2);
                } else {//苹果小程序
                    list = wifiRepository.getResultByBSSIDSecond(jo.getString("signalStrength"), jo.getString("BSSID"), 3);
                }
                Iterator<JSONObject> it2 = list.iterator();
                while(it2.hasNext()) { //将匹配到的wifi信息一组组取出
                    JSONObject jo2 = it2.next();
                    if(map.get(jo2.getString("loc_id")) != null) {
                        int v1 = map.get(jo2.getString("loc_id"));
                        int v2 = jo2.getInteger("COUNT(location_info.loc_id)");
                        int v = v1 + v2;
                        map.put(jo2.getString("loc_id"),v);
                    } else {
                        map.put(jo2.getString("loc_id"),jo2.getInteger("COUNT(location_info.loc_id)"));
                    }
                }
            }
            List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            if( list.size() <= 0) {
                return new JsonResult(5001, "缺少定位数据");
            }
            // 周边点
            HashMap<String, Double> localInfoMap = new HashMap<>();
            for (Map.Entry s : list)
            {
//                System.out.println(s.getKey() + ":" + s.getValue());
//                System.out.println(wifiRepository.getMatchLocal((String) s.getKey()).get(0).toString());
                // 计算在某个点上的欧几里得距离（直线距离）
                Double pointSum = 0.0;
                Iterator<Object> nnDisList = wifi_test_list.iterator();
                while( nnDisList.hasNext() ) { // 循环Wifi
                    Double levelSum = 0.0;
                    Double levelNum = 0.0;
                    JSONObject listNext = (JSONObject) nnDisList.next();
//                    System.out.println(listNext.toString());
//                    ArrayList<JSONObject> levellist = wifiRepository.getLevelList((String) s.getKey(), listNext.getString("BSSID"));
                    ArrayList<JSONObject> levellist = null;
                    if( type == 1 ) {  //安卓端
                        levellist = wifiRepository.getLevelList(listNext.getInteger("level"), (String) s.getKey(), listNext.getString("BSSID"));
                    } else {  //小程序端
                        levellist = wifiRepository.getLevelList(listNext.getInteger("signalStrength"), (String) s.getKey(), listNext.getString("BSSID"));
                    }
                    Iterator<JSONObject> levellistIte = levellist.iterator();
                    while( levellistIte.hasNext() ) {
                        JSONObject jo = levellistIte.next();
//                        System.out.println(jo.getInteger("levels"));
                        levelSum = levelSum + jo.getInteger("levels");
                        levelNum++;
                    }
                    Double k = levelSum / levelNum;
                    if(levelNum > 0) {
//                        pointSum = pointSum + (levelSum / levelNum) * (levelSum / levelNum);
                        pointSum = pointSum + k * k;
                    }
                }

                localInfoMap.put((String)s.getKey(), Math.sqrt(pointSum));
            }

            List<Map.Entry<String,Double>>lists=new ArrayList<Map.Entry<String,Double>>(localInfoMap.entrySet());
            Collections.sort(lists,new Comparator<Map.Entry<String, Double>>() {
                public int compare(Map.Entry<String, Double> o1,Map.Entry<String, Double> o2)
                {
                    double q1=o1.getValue();
                    double q2=o2.getValue();
                    double p=q2-q1;
                    if(p<0){
                        return 1;
                    }
                    else if(p==0){
                        return 0;
                    }
                    else
                        return -1;
                }
            });

            for(Map.Entry<String, Double> set:lists){
                System.out.println(set.getKey() + " " + set.getValue());
            }

            JSONObject temp = new JSONObject();
            if(num == 1) {
//                System.out.println(lists.get(0).getKey());
                JSONObject result = wifiRepository.getMatchLocal(lists.get(0).getKey()).get(0);

//                计算两点间距离（目标地和初始地点）
                GlobalCoordinates target = new GlobalCoordinates(result.getDouble("latitude"), result.getDouble("longitude"));
                double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
//                wifiRepository.saveTestRecord(
//                        String.format("%.6f", result.getDouble("latitude")),
//                        String.format("%.6f", result.getDouble("longitude")),
//                        wifi_test_list.toString(),
//                        wifi_test_list.size()
//                );
                wifiRepository.saveTestRecordIncludeDis(
                        String.format("%.6f", result.getDouble("latitude")),
                        String.format("%.6f", result.getDouble("longitude")),
                        wifi_test_list.toString(),
                        wifi_test_list.size(),
                        meter1
                );
                System.out.println("第1次存储");
                temp.put("result",result);
                temp.put("value",lists.get(0).getValue());
                ret_data.add(temp);
            } else {
//                循环list求出分母
                int m=0;
                Double mu = 0.0;
                for(Map.Entry<String, Double> set:lists){
                    mu = mu + 1/set.getValue();
                    m++;
                    if(m == num) {
                        break;
                    }
                }
//                System.out.println(mu);

                //开始循环根据权职求出经纬度
                Double latitude = 0.0;
                Double longitude = 0.0;

                int flag = 0;
                for(Map.Entry<String, Double> set:lists){
//                    System.out.println(set.getKey() +" "+set.getValue());
                    Double zi = 1 / set.getValue();
                    Double w = zi / mu;

                    ArrayList<JSONObject> arr = wifiRepository.getLocalInfoByLocalId(set.getKey());
//                    System.out.println(arr.get(0).getDouble("latitude"));
                    if( average == 0 ) { // 平均
                        latitude = latitude + arr.get(0).getDouble("latitude");
                        longitude = longitude + arr.get(0).getDouble("longitude");
                    } else {
                        latitude = latitude + arr.get(0).getDouble("latitude") * w;
                        longitude = longitude + arr.get(0).getDouble("longitude") * w;
                    }
                    flag++;
                    if( flag == num ) {
                        break;
                    }
                }
                if( average == 0 ) {
                    latitude = latitude / num;
                    longitude = longitude / num;
                }
                temp.put("latitude", String.format("%.6f", latitude));
                temp.put("longitude", String.format("%.6f", longitude));
                ret_data.add(temp);

                //                计算两点间距离
                GlobalCoordinates target = new GlobalCoordinates(latitude, longitude);
                double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);

//                wifiRepository.saveTestRecord(
//                        String.format("%.6f", latitude),
//                        String.format("%.6f", longitude),
//                        wifi_test_list.toString(),
//                        wifi_test_list.size()
//                );
                wifiRepository.saveTestRecordIncludeDis(
                        String.format("%.6f", latitude),
                        String.format("%.6f", longitude),
                        wifi_test_list.toString(),
                        wifi_test_list.size(),
                        meter1
                );
                System.out.println("第2次存储");
            }
        } else {
            JSONObject temp = new JSONObject();
            temp.put("massage","数据列表为空");
            ret_data.add(temp);
        }
        return new JsonResult(ret_data);
    }

    /*
     * 获取该点周围的点 按level排序
     */
    public ArrayList<JSONObject> getMatchPoint(JSONArray wifi_test_list, int num) {
        ArrayList<JSONObject> ret_data = new ArrayList<>();

        if( wifi_test_list != null ) {
//            map 计数
            HashMap<String, Integer> map = new HashMap<>();

            Iterator<Object> it = wifi_test_list.iterator();
            while( it.hasNext() ) {
                JSONObject jo = (JSONObject)it.next();
//                获取BSSID数组
//                if(jo.getString("BSSID").equals("8a:25:93:ea:24:64")) {
//                    System.out.println("遇到：" + jo.getString("BSSID") + ":" + jo.getString("SSID"));
//                    jo = (JSONObject)it.next();
//                    System.out.println("变为：" + jo.getString("BSSID") + ":" + jo.getString("SSID"));
//                }
                ArrayList<JSONObject> list = wifiRepository.getResultByBSSID(jo.getString("level"), jo.getString("BSSID"));
                Iterator<JSONObject> it2 = list.iterator();

                while(it2.hasNext()) {
                    JSONObject jo2 = it2.next();
                    if(map.get(jo2.getString("loc_id")) != null) {
                        int v1 = map.get(jo2.getString("loc_id"));
                        int v2 = jo2.getInteger("COUNT(location_info.loc_id)");
                        int v = v1 + v2;
                        map.put(jo2.getString("loc_id"),v);
                    } else {
                        map.put(jo2.getString("loc_id"),jo2.getInteger("COUNT(location_info.loc_id)"));
                    }
//                    System.out.println(jo2.getString("loc_id") + "::::" + jo2.getInteger("COUNT(location_info.loc_id)"));
                }

            }
//            System.out.println("循环map");
//            for(Map.Entry<String, Integer> entry : map.entrySet()){
//                String mapKey = entry.getKey();
//                Integer mapValue = entry.getValue();
//                System.out.println(mapKey+":"+mapValue);
//            }

            Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
            List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                {
                    //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                    //按照value值，从大到小排序
                return o2.getValue() - o1.getValue();

                    //按照value值，用compareTo()方法默认是从小到大排序
//                    return o1.getValue().compareTo(o2.getValue());
                }
            });
            int il = 0;
//            System.out.println(num);
            for (Map.Entry s : list)
            {
                JSONObject temp = new JSONObject();
//                System.out.println(s.getKey() + ":" + s.getValue());
//                System.out.println(wifiRepository.getMatchLocal((String) s.getKey()).get(0).toString());
                temp.put("result",wifiRepository.getMatchLocal((String) s.getKey()).get(0));
                temp.put("value",s.getValue());
                ret_data.add(temp);
                il++;
//                System.out.println(il == num);
                if(il == num) {
                    break;
                }
            }
        }
//        数据库保存测试数据
//        System.out.println(wifi_test_list.toString());
//        wifiRepository.saveTestRecord(
//                ret_data.get(0).getJSONObject("result").getString("latitude"),
//                ret_data.get(0).getJSONObject("result").getString("longitude"),
//                wifi_test_list.toString(),
//                wifi_test_list.size()
//                );

        return ret_data;
    }

    public ArrayList<JSONObject> WifiRecordByLocId(String loc_id) {
        ArrayList<JSONObject> ret_data = new ArrayList<>();
        JSONObject temp = new JSONObject();
        temp.put("local_info_list",wifiRepository.getLocalList(loc_id));
        ret_data.add(temp);
        return ret_data;
    }
    /*
     * 根据wifi_rec_id获取画图数据
     * 格式整理
     */
    public ArrayList<JSONObject> WifiRecordDetail(String wifi_rec_id) {
        ArrayList<JSONObject> ret_data = new ArrayList<>();
        JSONObject temp = new JSONObject();

        HashMap<String, ArrayList> map = new HashMap<>();

        ArrayList<JSONObject> wifi_id_list = wifiRepository.getWifiId(wifi_rec_id);
        Iterator<JSONObject> it = wifi_id_list.iterator();

        try {
            while( it.hasNext() ) {
                JSONObject jo = it.next();

//                System.out.println("ja:" + jo.getString("wifi_id"));

                ArrayList<JSONObject> detail_list = wifiRepository.getWifiDetails(jo.getString("wifi_id"));
//                System.out.println(detail_list.toString());
                Iterator<JSONObject> it2 = detail_list.iterator();
                while(it2.hasNext()) {
                    JSONObject jo2 = it2.next();
//                System.out.println(jo2.getString("BSSID"));
                    if(map.get(jo2.getString("BSSID")) != null) {
                        ArrayList v1 = map.get(jo2.getString("BSSID"));
                        v1.add(jo2.getInteger("level"));
                        map.put(jo2.getString("BSSID"),v1);
                    } else {
                        ArrayList Alist = new ArrayList();
                        Alist.add(jo2.getInteger("level"));
                        map.put(jo2.getString("BSSID"), Alist);
                    }
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        ArrayList result = new ArrayList();

//        System.out.println("循环map");
        for(Map.Entry<String, ArrayList> entry : map.entrySet()){
            JSONObject resulttemp = new JSONObject();
            String mapKey = entry.getKey();
            ArrayList mapValue = entry.getValue();
            resulttemp.put("name",wifiRepository.getSSIDByBSSID(mapKey).get(0).getString("SSID"));
            resulttemp.put("type","line");
            resulttemp.put("data",mapValue);
            result.add(resulttemp);
        }

        temp.put("resu", result);
        ret_data.add(temp);
        return ret_data;
    }

    /*   已知RSSI求距离
     *   d = 10^(A-(abs(rssi)) / (10 * n))  信号强度  公式 A-40
     *   #define N 45          //N = 10 * n ,其中n为环境衰减因子，3.25-4.5
     *   #define A -35           //接收机和发射机间隔1m时的信号强度
     *   参数目前使用网上参数 具体实际后期再调
     */
    public float rssiGetDis(int rssi) {
        float iu;
        iu = (float)(40 - rssi ) / (float)45;
        float distance = (float) pow(10.0, iu);//计算以x为底数的y次幂     功能与pow一致，只是输入与输出皆为浮点数

        return distance;
    }

    //    多点定位 返回经纬度
    public Object getMatchPoints(JSONArray wifi_test_list, int i) {
        ArrayList<JSONObject> ret_data = new ArrayList<>();
//        JSONObject temp = new JSONObject();  返回的数据

        ArrayList<JSONObject> arr = this.getMatchPoint(wifi_test_list, i);

        // 循环获取value总 值
        Double valnumber = 0.0;
        Iterator<JSONObject> numval = arr.iterator();
        while( numval.hasNext() ) {
            valnumber = valnumber + numval.next().getDouble("value");
//            System.out.println("valnumber:" + valnumber);
        }
//        System.out.println("val" + valnumber);

        Double resultLatitude = 0.0;
        Double resultLongitude = 0.0;
        int averageNum = 0, size = arr.size();
//        System.out.println(arr.size());
        Iterator<JSONObject> it = arr.iterator();
        while( it.hasNext() ) {
            JSONObject iti = it.next();
            JSONObject jo = iti.getJSONObject("result");
            Double v = iti.getDouble("value");
//            System.out.println("1:" + v);
//            System.out.println(valnumber);
//            System.out.println( v / valnumber );
//            resultLatitude = resultLatitude + jo.getDouble("latitude") * size;
//            resultLongitude = resultLongitude + jo.getDouble("longitude") * size;
            resultLatitude = resultLatitude + jo.getDouble("latitude") * ( v / valnumber );
            resultLongitude = resultLongitude + jo.getDouble("longitude") * ( v / valnumber );
            averageNum = averageNum + size;
            size--;
        }

//        System.out.println(averageNum);
//        System.out.println(size);

//        System.out.println(arr.toString());

        JSONObject temp = new JSONObject();
//        temp.put("latitude", String.format("%.6f", resultLatitude / averageNum));
//        temp.put("longitude", String.format("%.6f", resultLongitude / averageNum));
        temp.put("latitude", String.format("%.6f", resultLatitude));
        temp.put("longitude", String.format("%.6f", resultLongitude));
        ret_data.add(temp);

        wifiRepository.saveTestRecord(
                String.format("%.6f", resultLatitude),
                String.format("%.6f", resultLongitude),
                wifi_test_list.toString(),
                wifi_test_list.size()
        );

        return ret_data;
    }

    public ArrayList<JSONObject> WifiRecordDifPhone() {
        ArrayList<JSONObject> wifi_id_list = wifiRepository.getDifPhoneInf();
        JSONArray list = wifi_id_list.get(0).getJSONObject("wifidetail").getJSONArray("wifi_test_list");
        JSONArray list2 = wifi_id_list.get(1).getJSONObject("wifidetail").getJSONArray("wifi_test_list");

        HashMap<String, JSONArray> map = new HashMap<>();

        Iterator<Object> it = list.iterator();
        while(it.hasNext()) {
            JSONObject jo = (JSONObject) it.next();
//                System.out.println(jo2.getString("BSSID"));
            if(map.get(jo.getString("BSSID")) != null) {
                JSONArray arr = map.get(jo.getString("BSSID"));
                arr.add(jo);
                map.put(jo.getString("BSSID"), arr);
            } else {
                JSONArray Alist = new JSONArray();
                Alist.add(jo);
                map.put(jo.getString("BSSID"), Alist);
            }
        }

        Iterator<Object> it2 = list2.iterator();
        while(it2.hasNext()) {
            JSONObject jo2 = (JSONObject) it2.next();
            if(map.get(jo2.getString("BSSID")) != null) {
                JSONArray arr = map.get(jo2.getString("BSSID"));
                arr.add(jo2);
                map.put(jo2.getString("BSSID"), arr);
            } else {
                JSONArray Blist = new JSONArray();
                Blist.add(jo2);
                map.put(jo2.getString("BSSID"), Blist);
            }
        }

        ArrayList<JSONObject> ret_data = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<Integer> l1 = new ArrayList<>();
        ArrayList<Integer> l2 = new ArrayList<>();

//        计算平均误差
        Double wSum = 0.0;
        Integer count = 0;
        try{
            for(Map.Entry<String, JSONArray> entry : map.entrySet()){
                String mapKey = entry.getKey();
                JSONArray mapValue = entry.getValue();
//                System.out.println(mapKey+":"+mapValue.toString());
                if(mapValue.size() == 2 ) {
                    name.add(mapValue.getJSONObject(0).getString("SSID"));
                    l1.add(mapValue.getJSONObject(0).getInteger("level"));
                    l2.add(mapValue.getJSONObject(1).getInteger("level"));
                    wSum = wSum + (mapValue.getJSONObject(0).getInteger("level") - mapValue.getJSONObject(1).getInteger("level"));
                    count++;
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        System.out.println(wSum / count);
//        System.out.println(name.size());
//        for(int i =0;i<name.size();i++) {
//            System.out.println("name:" + name.get(i));
//            System.out.println("l1:" + l1.get(i));
//            System.out.println("l2:" + l2.get(i));
//        }
        JSONObject temp1 = new JSONObject();
        temp1.put("name", wifi_id_list.get(0).getString("remarks"));
        temp1.put("type", "line");
        temp1.put("data", l1);

        JSONObject temp2 = new JSONObject();
        temp2.put("name",  wifi_id_list.get(1).getString("remarks"));
        temp2.put("type", "line");
        temp2.put("data", l2);

        ArrayList<JSONObject> data_series = new ArrayList<>();
        data_series.add(temp1);
        data_series.add(temp2);

        JSONObject temp3 = new JSONObject();
        temp3.put("data", name);
        temp3.put("data_series", data_series);

        ret_data.add(temp3);

        return ret_data;
    }

    public ArrayList<JSONObject> wifiTestSave() {
        ArrayList<JSONObject> ret_data = new ArrayList<>();

        ArrayList<JSONObject> wifi_id_list = wifiRepository.getPointList(514);
        System.out.println(wifi_id_list.get(0).getJSONObject("wifidetail").getJSONArray("wifi_test_list"));
        JSONArray list = wifi_id_list.get(0).getJSONObject("wifidetail").getJSONArray("wifi_test_list");
        Iterator<Object> it2 = list.iterator();
        while(it2.hasNext()) {
            JSONObject jo2 = (JSONObject) it2.next();

            System.out.println(jo2.getString("BSSID"));
            wifiRepository.saveTestRecordDetials(jo2.getString("SSID"), jo2.getString("BSSID"), jo2.getString("frequency"), jo2.getString("level"),jo2.getString("timestamp"),  jo2.getString("capabilities"));
        }

        return ret_data;
    }

}
