package com.ksdigtalnomad.koala.helpers.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HashMapHelper {
    public static <T> HashMap<T, Integer> sort(HashMap<T, Integer> map){
        // value 내림차순으로 정렬하고, value가 같으면 key 오름차순으로 정렬
        List<Map.Entry<T, Integer>> list = new LinkedList(map.entrySet());

        Collections.sort(list, (Map.Entry<T, Integer> o1, Map.Entry<T, Integer> o2)->{
            int comparision = (o1.getValue() - o2.getValue()) * -1;
            return comparision;
        });

        // 순서유지를 위해 LinkedHashMap을 사용
        HashMap<T, Integer> sortedMap = new LinkedHashMap<>();
        for(Iterator<Map.Entry<T, Integer>> iter = list.iterator(); iter.hasNext();){
            Map.Entry<T, Integer> entry = iter.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static <T> ArrayList<HashMap<T, Integer>> sortToArrayList(HashMap<T, Integer> map){
        HashMap<T, Integer> mapTemp = (HashMap<T, Integer>) map.clone();
        // value 내림차순으로 정렬하고, value가 같으면 key 오름차순으로 정렬
        List<Map.Entry<T, Integer>> list = new LinkedList(mapTemp.entrySet());

        Collections.sort(list, (Map.Entry<T, Integer> o1, Map.Entry<T, Integer> o2)->{
            int comparision = (o1.getValue() - o2.getValue()) * -1;
            return comparision;
        });

        ArrayList<HashMap<T, Integer>> arrayList = new ArrayList<>();
        for(Iterator<Map.Entry<T, Integer>> iter = list.iterator(); iter.hasNext();){
            Map.Entry<T, Integer> entry = iter.next();
            HashMap<T, Integer> sortedMap = new HashMap<>();
            sortedMap.put(entry.getKey(), entry.getValue());
            arrayList.add(sortedMap);
        }
        return arrayList;
    }

}
