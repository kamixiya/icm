package com.kamixiya.icm.core.json;

import java.text.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 扩展jackson日期格式化支持格式(yyyy-MM-dd'T'HH:mm:ss.SSS'Z',yyyy-MM-dd)
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public class ObjectMapperDateFormatExtend extends DateFormat {

    private static final long serialVersionUID = 1L;
    private final SimpleDateFormat[] DATE_FORMATS = {
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
            new SimpleDateFormat("yyyy-MM-dd")
    };
    private DateFormat dateFormat;

    public ObjectMapperDateFormatExtend(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        this.calendar = (Calendar) dateFormat.getCalendar().clone();
        this.numberFormat = (NumberFormat) dateFormat.getNumberFormat().clone();
    }


    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo,
                               FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }


    @Override
    public Date parse(String source, ParsePosition pos) {
        Date date = null;

        try {
            date = dateFormat.parse(source, pos);
        } catch (Exception e) {
            // nothing to do
        }
        if (date == null) {
            for (SimpleDateFormat sdf : DATE_FORMATS) {
                try {
                    date = sdf.parse(source, pos);
                    if (date != null) {
                        break;
                    }
                } catch (Exception ex) {
                    // nothing to do
                }
            }
        }

        return date;
    }

    @Override
    public Object clone() {
        return new ObjectMapperDateFormatExtend((DateFormat) this.dateFormat.clone());
    }
}
