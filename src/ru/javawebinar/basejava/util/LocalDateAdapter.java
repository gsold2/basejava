package ru.javawebinar.basejava.util;

import java.time.YearMonth;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, YearMonth>
{
    @Override
    public String marshal(YearMonth date)
    {
        if ( date == null ) return null;
        return date.toString();
    }

    @Override
    public YearMonth unmarshal(String string)
    {
        return YearMonth.parse(string);
    }
}