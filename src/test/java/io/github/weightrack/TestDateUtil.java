package io.github.weightrack;

import io.github.weightrack.utils.DateUtil;
import org.junit.Test;


public class TestDateUtil {
    @Test
    public void test() {
        System.out.println(DateUtil.getTodayStartTime());
        System.out.println(DateUtil.getTodayEndTime());
        System.out.println(DateUtil.getFirstDayOfMonth());
        System.out.println(DateUtil.getLastDayOfMonth());
        System.out.println(DateUtil.getFirstDayOfYear());
        System.out.println(DateUtil.getLastDayOfYear());
    }
}
