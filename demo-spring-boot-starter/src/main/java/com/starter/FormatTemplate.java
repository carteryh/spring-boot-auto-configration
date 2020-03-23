package com.starter;


import com.starter.autoconfiguration.DemoProperties;
import com.starter.format.FormatProcessor;

public class FormatTemplate {

    private FormatProcessor formatProcessor;

    private DemoProperties demoProperties;

    public FormatTemplate(DemoProperties demoProperties, FormatProcessor formatProcessor) {
        this.demoProperties = demoProperties;
        this.formatProcessor = formatProcessor;
    }

    public <T> String doFormat(T obj){

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("begin:Execute format").append("<br/>");
        stringBuilder.append("HelloProperties:").append(formatProcessor.format(demoProperties)).append("<br/>");
        stringBuilder.append("Obj format result:").append(formatProcessor.format(obj)).append("<br/>");
        return stringBuilder.toString();

    }
}
