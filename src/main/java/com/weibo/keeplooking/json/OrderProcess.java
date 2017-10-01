package com.weibo.keeplooking.json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class OrderProcess {

    @Test
    public void process() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/tmp/order.json"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        PrintWriter pw = new PrintWriter(new FileWriter("D:/tmp/order_formatted.csv"));

        pw.print("orderNo");
        pw.print(",");
        pw.print("ticketPrice");
        pw.print(",");
        pw.print("payment.expressPrice");
        pw.print(",");
        pw.print("payment.realPay");
        pw.print(",");
        pw.print("payment.ticketPrice");
        pw.print(",");
        pw.print("ticket.price");
        pw.print(",");
        pw.print("ticket.price");
        pw.print(",");
        pw.print("ticket.price");
        pw.print(",");
        pw.print("ticket.price");
        pw.print(",");
        pw.print("ticket.price");
        pw.println();

        JSONObject outerHits = JSON.parseObject(sb.toString()).getJSONObject("hits");
        int total = outerHits.getIntValue("total");
        if (total > 0) {
            JSONArray innerhits = outerHits.getJSONArray("hits");
            for (int i = 0; i < innerhits.size(); i++) {
                JSONObject source = innerhits.getJSONObject(i).getJSONObject("_source");
                pw.print(source.getString("orderNo"));
                pw.print(",");

                pw.print(source.getFloat("ticketPrice"));
                pw.print(",");

                JSONArray payments = source.getJSONArray("payment");
                JSONObject payment = (JSONObject) payments.get(0);
                pw.print(payment.getFloat("expressPrice"));
                pw.print(",");
                pw.print(payment.getFloat("realPay"));
                pw.print(",");
                pw.print(payment.getFloat("ticketPrice"));
                pw.print(",");

                JSONArray tickets = source.getJSONArray("ticket");
                for (int j = 0; j < tickets.size(); j++) {
                    JSONObject ticket = (JSONObject) tickets.get(j);
                    pw.print(ticket.getFloat("price"));
                    pw.print(",");
                }
                pw.println();
            }
        }
        pw.close();
    }

}
