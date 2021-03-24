package com.restful.daily_click.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.WifiRecordEntity;
import com.restful.daily_click.repository.LocationRepository;
import com.restful.daily_click.repository.WifiRecordRepository;
import com.restful.daily_click.repository.WifiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Integer.valueOf;


@Service
public class WifiRecordService {
    @Autowired
    WifiRecordRepository wifiRecordRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    WifiRepository wifiRepository;
    public ArrayList<WifiRecordEntity> getWifiRecord(String account){
        ArrayList<Integer> loc_id_list = locationRepository.getLocIdByAccount(account);
        return wifiRecordRepository.getWifiRecordList(loc_id_list);
    }

//    public int addWifiRecord(int loc_id, String phone_info, JSONArray wifi_info_list){
//        wifiRecordRepository.AddWifiRecord(loc_id,phone_info);
//        int wifi_rec_id = wifiRecordRepository.getLastInsertId();
//        List<WifiInfoEntity> save_wifi_list = new ArrayList<WifiInfoEntity>();
//        for(int i=0;i<wifi_info_list.size();i++){
//            JSONObject json_temp = wifi_info_list.getJSONObject(i);
//            WifiInfoEntity temp = new WifiInfoEntity();
//            temp.setWifiRecId(wifi_rec_id);
//            temp.setBssid(json_temp.getString("BSSID"));
//            temp.setFrequency(json_temp.getInteger("frequency"));
//            temp.setLevel(json_temp.getInteger("level"));
//            temp.setSsid(json_temp.getString("SSID"));
//            temp.setTimestamp(json_temp.getString("timestamp"));
//            Timestamp time= new Timestamp(System.currentTimeMillis());
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String timeStr = df.format(time);
//            time = Timestamp.valueOf(timeStr);
//            System.out.println(time);
////            temp.setCreateTime(time);
//            save_wifi_list.add(temp);
//        }
//
////        System.out.println(save_wifi_list.toString());
//        wifiRepository.saveAll(save_wifi_list);
////        arrayPrint(save_wifi_list);
//        return wifi_rec_id;
//    }

    public int addWifiRecord(int loc_id, String phone_info, JSONArray wifi_test_list, int type){
        wifiRecordRepository.AddWifiRecord(loc_id,phone_info);
        int wifi_rec_id = wifiRecordRepository.getLastInsertId();
        for(int i=0;i<wifi_test_list.size();i++){
            JSONArray temp = wifi_test_list.getJSONArray(i);
            if( temp  != null ) {
                //            记录wifi record
//                String wifi_details = JSON.toJSONString(temp);
                wifiRepository.addWifi(wifi_rec_id);
                int wifi_id = wifiRepository.getLastInsertId();
//                System.out.println("wifi_id" + wifi_id);
//                记录wifi detial
                Iterator<Object> it = temp.iterator();
                while( it.hasNext() ) {
                    JSONObject jo = (JSONObject)it.next();
                    if( type == 1 ) { // 安卓端
                        wifiRecordRepository.AddWifiDetial(wifi_id, jo.getString("SSID"), jo.getString("BSSID"), jo.getString("frequency"), jo.getString("level"), jo.getString("timestamp"), jo.getString("capabilities"), String.valueOf(valueOf(jo.getString("level")) + 90), type);
                    } else if( type == 2 ) { // 安卓小程序
                        wifiRecordRepository.AddWifiDetial(wifi_id, jo.getString("SSID"), jo.getString("BSSID"), "0", jo.getString("signalStrength"), "", "", jo.getString("signalStrength"), type);
                    } else if( type == 3 ) { // 苹果小程序
                        wifiRecordRepository.AddWifiDetial(wifi_id, jo.getString("SSID"), jo.getString("BSSID"), "0", String.valueOf(valueOf(jo.getString("signalStrength")) * 100 ), "", "", jo.getString("signalStrength"), type);
                    }

                }
            }

        }
        return wifi_rec_id;
    }
}
