package com.example.center.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestJvmOutOfMemory {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            String str = "";
            for (int j = 0; j < 1000; j++) {
                str += UUID.randomUUID().toString();
            }
            list.add(str);
        }
        System.out.println("ok");
        //-Dfile.encoding=UTF-8 ‐Xms8m ‐Xmx8m ‐XX:+HeapDumpOnOutOfMemoryError
        //linux
        ///test/logs 这个路径要存在
        // java -Dfile.encoding=UTF-8 -Xms8m -Xmx8m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/test/logs TestJvmOutOfMemory
    }

}
