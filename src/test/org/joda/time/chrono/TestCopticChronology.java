/*
 *  Copyright 2001-2005 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.time.chrono;

import java.util.Locale;
import java.util.TimeZone;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;

/**
 * This class is a Junit unit test for CopticChronology.
 *
 * @author Stephen Colebourne
 */
public class TestCopticChronology extends TestCase {

    private static int SKIP = 1 * DateTimeConstants.MILLIS_PER_DAY;

    private static final DateTimeZone PARIS = DateTimeZone.forID("Europe/Paris");
    private static final DateTimeZone LONDON = DateTimeZone.forID("Europe/London");
    private static final DateTimeZone TOKYO = DateTimeZone.forID("Asia/Tokyo");
    private static final Chronology COPTIC_UTC = Chronology.getCopticUTC();
    private static final Chronology JULIAN_UTC = Chronology.getJulianUTC();
    private static final Chronology ISO_UTC = Chronology.getISOUTC();

    long y2002days = 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 
                     366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 
                     365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 +
                     366 + 365;
    // 2002-06-09
    private long TEST_TIME_NOW =
            (y2002days + 31L + 28L + 31L + 30L + 31L + 9L -1L) * DateTimeConstants.MILLIS_PER_DAY;

    private DateTimeZone originalDateTimeZone = null;
    private TimeZone originalTimeZone = null;
    private Locale originalLocale = null;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        SKIP = 1 * DateTimeConstants.MILLIS_PER_DAY;
        return new TestSuite(TestCopticChronology.class);
    }

    public TestCopticChronology(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(TEST_TIME_NOW);
        originalDateTimeZone = DateTimeZone.getDefault();
        originalTimeZone = TimeZone.getDefault();
        originalLocale = Locale.getDefault();
        DateTimeZone.setDefault(LONDON);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        Locale.setDefault(Locale.UK);
    }

    protected void tearDown() throws Exception {
        DateTimeUtils.setCurrentMillisSystem();
        DateTimeZone.setDefault(originalDateTimeZone);
        TimeZone.setDefault(originalTimeZone);
        Locale.setDefault(originalLocale);
        originalDateTimeZone = null;
        originalTimeZone = null;
        originalLocale = null;
    }

    //-----------------------------------------------------------------------
    public void testFactoryUTC() {
        assertEquals(DateTimeZone.UTC, CopticChronology.getInstanceUTC().getZone());
        assertSame(CopticChronology.class, CopticChronology.getInstanceUTC().getClass());
    }

    public void testFactory() {
        assertEquals(LONDON, CopticChronology.getInstance().getZone());
        assertSame(CopticChronology.class, CopticChronology.getInstance().getClass());
    }

    public void testFactory_Zone() {
        assertEquals(TOKYO, CopticChronology.getInstance(TOKYO).getZone());
        assertEquals(PARIS, CopticChronology.getInstance(PARIS).getZone());
        assertEquals(LONDON, CopticChronology.getInstance(null).getZone());
        assertSame(CopticChronology.class, CopticChronology.getInstance(TOKYO).getClass());
    }

    //-----------------------------------------------------------------------
    public void testEquality() {
        assertSame(CopticChronology.getInstance(TOKYO), CopticChronology.getInstance(TOKYO));
        assertSame(CopticChronology.getInstance(LONDON), CopticChronology.getInstance(LONDON));
        assertSame(CopticChronology.getInstance(PARIS), CopticChronology.getInstance(PARIS));
        assertSame(CopticChronology.getInstanceUTC(), CopticChronology.getInstanceUTC());
        assertSame(CopticChronology.getInstance(), CopticChronology.getInstance(LONDON));
    }

    public void testWithUTC() {
        assertSame(CopticChronology.getInstanceUTC(), CopticChronology.getInstance(LONDON).withUTC());
        assertSame(CopticChronology.getInstanceUTC(), CopticChronology.getInstance(TOKYO).withUTC());
        assertSame(CopticChronology.getInstanceUTC(), CopticChronology.getInstanceUTC().withUTC());
        assertSame(CopticChronology.getInstanceUTC(), CopticChronology.getInstance().withUTC());
    }

    public void testWithZone() {
        assertSame(CopticChronology.getInstance(TOKYO), CopticChronology.getInstance(TOKYO).withZone(TOKYO));
        assertSame(CopticChronology.getInstance(LONDON), CopticChronology.getInstance(TOKYO).withZone(LONDON));
        assertSame(CopticChronology.getInstance(PARIS), CopticChronology.getInstance(TOKYO).withZone(PARIS));
        assertSame(CopticChronology.getInstance(LONDON), CopticChronology.getInstance(TOKYO).withZone(null));
        assertSame(CopticChronology.getInstance(PARIS), CopticChronology.getInstance().withZone(PARIS));
        assertSame(CopticChronology.getInstance(PARIS), CopticChronology.getInstanceUTC().withZone(PARIS));
    }

    public void testToString() {
        assertEquals("CopticChronology[Europe/London]", CopticChronology.getInstance(LONDON).toString());
        assertEquals("CopticChronology[Asia/Tokyo]", CopticChronology.getInstance(TOKYO).toString());
        assertEquals("CopticChronology[Europe/London]", CopticChronology.getInstance().toString());
        assertEquals("CopticChronology[UTC]", CopticChronology.getInstanceUTC().toString());
    }

    //-----------------------------------------------------------------------
    public void testDurationFields() {
        assertEquals("eras", CopticChronology.getInstance().eras().getName());
        assertEquals("centuries", CopticChronology.getInstance().centuries().getName());
        assertEquals("years", CopticChronology.getInstance().years().getName());
        assertEquals("weekyears", CopticChronology.getInstance().weekyears().getName());
        assertEquals("months", CopticChronology.getInstance().months().getName());
        assertEquals("weeks", CopticChronology.getInstance().weeks().getName());
        assertEquals("days", CopticChronology.getInstance().days().getName());
        assertEquals("halfdays", CopticChronology.getInstance().halfdays().getName());
        assertEquals("hours", CopticChronology.getInstance().hours().getName());
        assertEquals("minutes", CopticChronology.getInstance().minutes().getName());
        assertEquals("seconds", CopticChronology.getInstance().seconds().getName());
        assertEquals("millis", CopticChronology.getInstance().millis().getName());
        
        assertEquals(false, CopticChronology.getInstance().eras().isSupported());
        assertEquals(true, CopticChronology.getInstance().centuries().isSupported());
        assertEquals(true, CopticChronology.getInstance().years().isSupported());
        assertEquals(true, CopticChronology.getInstance().weekyears().isSupported());
        assertEquals(true, CopticChronology.getInstance().months().isSupported());
        assertEquals(true, CopticChronology.getInstance().weeks().isSupported());
        assertEquals(true, CopticChronology.getInstance().days().isSupported());
        assertEquals(true, CopticChronology.getInstance().halfdays().isSupported());
        assertEquals(true, CopticChronology.getInstance().hours().isSupported());
        assertEquals(true, CopticChronology.getInstance().minutes().isSupported());
        assertEquals(true, CopticChronology.getInstance().seconds().isSupported());
        assertEquals(true, CopticChronology.getInstance().millis().isSupported());
        
        assertEquals(false, CopticChronology.getInstance().centuries().isPrecise());
        assertEquals(false, CopticChronology.getInstance().years().isPrecise());
        assertEquals(false, CopticChronology.getInstance().weekyears().isPrecise());
        assertEquals(false, CopticChronology.getInstance().months().isPrecise());
        assertEquals(false, CopticChronology.getInstance().weeks().isPrecise());
        assertEquals(false, CopticChronology.getInstance().days().isPrecise());
        assertEquals(false, CopticChronology.getInstance().halfdays().isPrecise());
        assertEquals(true, CopticChronology.getInstance().hours().isPrecise());
        assertEquals(true, CopticChronology.getInstance().minutes().isPrecise());
        assertEquals(true, CopticChronology.getInstance().seconds().isPrecise());
        assertEquals(true, CopticChronology.getInstance().millis().isPrecise());
        
        assertEquals(false, CopticChronology.getInstanceUTC().centuries().isPrecise());
        assertEquals(false, CopticChronology.getInstanceUTC().years().isPrecise());
        assertEquals(false, CopticChronology.getInstanceUTC().weekyears().isPrecise());
        assertEquals(true, CopticChronology.getInstanceUTC().months().isPrecise());
        assertEquals(true, CopticChronology.getInstanceUTC().weeks().isPrecise());
        assertEquals(true, CopticChronology.getInstanceUTC().days().isPrecise());
        assertEquals(true, CopticChronology.getInstanceUTC().halfdays().isPrecise());
        assertEquals(true, CopticChronology.getInstanceUTC().hours().isPrecise());
        assertEquals(true, CopticChronology.getInstanceUTC().minutes().isPrecise());
        assertEquals(true, CopticChronology.getInstanceUTC().seconds().isPrecise());
        assertEquals(true, CopticChronology.getInstanceUTC().millis().isPrecise());
    }

    public void testDateFields() {
        assertEquals("era", CopticChronology.getInstance().era().getName());
        assertEquals("centuryOfEra", CopticChronology.getInstance().centuryOfEra().getName());
        assertEquals("yearOfCentury", CopticChronology.getInstance().yearOfCentury().getName());
        assertEquals("yearOfEra", CopticChronology.getInstance().yearOfEra().getName());
        assertEquals("year", CopticChronology.getInstance().year().getName());
        assertEquals("monthOfYear", CopticChronology.getInstance().monthOfYear().getName());
        assertEquals("weekyearOfCentury", CopticChronology.getInstance().weekyearOfCentury().getName());
        assertEquals("weekyear", CopticChronology.getInstance().weekyear().getName());
        assertEquals("weekOfWeekyear", CopticChronology.getInstance().weekOfWeekyear().getName());
        assertEquals("dayOfYear", CopticChronology.getInstance().dayOfYear().getName());
        assertEquals("dayOfMonth", CopticChronology.getInstance().dayOfMonth().getName());
        assertEquals("dayOfWeek", CopticChronology.getInstance().dayOfWeek().getName());
        
        assertEquals(true, CopticChronology.getInstance().era().isSupported());
        assertEquals(true, CopticChronology.getInstance().centuryOfEra().isSupported());
        assertEquals(true, CopticChronology.getInstance().yearOfCentury().isSupported());
        assertEquals(true, CopticChronology.getInstance().yearOfEra().isSupported());
        assertEquals(true, CopticChronology.getInstance().year().isSupported());
        assertEquals(true, CopticChronology.getInstance().monthOfYear().isSupported());
        assertEquals(true, CopticChronology.getInstance().weekyearOfCentury().isSupported());
        assertEquals(true, CopticChronology.getInstance().weekyear().isSupported());
        assertEquals(true, CopticChronology.getInstance().weekOfWeekyear().isSupported());
        assertEquals(true, CopticChronology.getInstance().dayOfYear().isSupported());
        assertEquals(true, CopticChronology.getInstance().dayOfMonth().isSupported());
        assertEquals(true, CopticChronology.getInstance().dayOfWeek().isSupported());
    }

    public void testTimeFields() {
        assertEquals("halfdayOfDay", CopticChronology.getInstance().halfdayOfDay().getName());
        assertEquals("clockhourOfHalfday", CopticChronology.getInstance().clockhourOfHalfday().getName());
        assertEquals("hourOfHalfday", CopticChronology.getInstance().hourOfHalfday().getName());
        assertEquals("clockhourOfDay", CopticChronology.getInstance().clockhourOfDay().getName());
        assertEquals("hourOfDay", CopticChronology.getInstance().hourOfDay().getName());
        assertEquals("minuteOfDay", CopticChronology.getInstance().minuteOfDay().getName());
        assertEquals("minuteOfHour", CopticChronology.getInstance().minuteOfHour().getName());
        assertEquals("secondOfDay", CopticChronology.getInstance().secondOfDay().getName());
        assertEquals("secondOfMinute", CopticChronology.getInstance().secondOfMinute().getName());
        assertEquals("millisOfDay", CopticChronology.getInstance().millisOfDay().getName());
        assertEquals("millisOfSecond", CopticChronology.getInstance().millisOfSecond().getName());
        
        assertEquals(true, CopticChronology.getInstance().halfdayOfDay().isSupported());
        assertEquals(true, CopticChronology.getInstance().clockhourOfHalfday().isSupported());
        assertEquals(true, CopticChronology.getInstance().hourOfHalfday().isSupported());
        assertEquals(true, CopticChronology.getInstance().clockhourOfDay().isSupported());
        assertEquals(true, CopticChronology.getInstance().hourOfDay().isSupported());
        assertEquals(true, CopticChronology.getInstance().minuteOfDay().isSupported());
        assertEquals(true, CopticChronology.getInstance().minuteOfHour().isSupported());
        assertEquals(true, CopticChronology.getInstance().secondOfDay().isSupported());
        assertEquals(true, CopticChronology.getInstance().secondOfMinute().isSupported());
        assertEquals(true, CopticChronology.getInstance().millisOfDay().isSupported());
        assertEquals(true, CopticChronology.getInstance().millisOfSecond().isSupported());
    }

    //-----------------------------------------------------------------------
    public void testEpoch() {
        DateTime epoch = new DateTime(1, 1, 1, 0, 0, 0, 0, COPTIC_UTC);
        assertEquals(new DateTime(284, 8, 29, 0, 0, 0, 0, JULIAN_UTC), epoch.withChronology(JULIAN_UTC));
    }

    public void testEra() {
        assertEquals(1, CopticChronology.AM);
        try {
            new DateTime(-1, 13, 5, 0, 0, 0, 0, COPTIC_UTC);
            fail();
        } catch (IllegalArgumentException ex) {}
    }

    //-----------------------------------------------------------------------
    /**
     * Tests era, year, monthOfYear, dayOfMonth and dayOfWeek.
     */
    public void testCalendar() {
        if (TestAll.FAST) {
            return;
        }
        System.out.println("\nTestCopticChronology.testCalendar");
        DateTime epoch = new DateTime(1, 1, 1, 0, 0, 0, 0, COPTIC_UTC);
        long millis = epoch.getMillis();
        long end = new DateTime(3000, 1, 1, 0, 0, 0, 0, ISO_UTC).getMillis();
        DateTimeField dayOfWeek = COPTIC_UTC.dayOfWeek();
        DateTimeField dayOfYear = COPTIC_UTC.dayOfYear();
        DateTimeField dayOfMonth = COPTIC_UTC.dayOfMonth();
        DateTimeField monthOfYear = COPTIC_UTC.monthOfYear();
        DateTimeField year = COPTIC_UTC.year();
        DateTimeField yearOfEra = COPTIC_UTC.yearOfEra();
        DateTimeField era = COPTIC_UTC.era();
        int expectedDOW = 5;
        int expectedDOY = 1;
        int expectedDay = 1;
        int expectedMonth = 1;
        int expectedYear = 1;
        while (millis < end) {
            int dowValue = dayOfWeek.get(millis);
            int doyValue = dayOfYear.get(millis);
            int dayValue = dayOfMonth.get(millis);
            int monthValue = monthOfYear.get(millis);
            int yearValue = year.get(millis);
            int yearOfEraValue = yearOfEra.get(millis);
            int eraValue = era.get(millis);
            int monthLen = dayOfMonth.getMaximumValue(millis);
            if (monthValue < 1 || monthValue > 13) {
                fail("Bad month: " + millis);
            }
            
            // test era
            assertEquals(1, era.get(millis));
            assertEquals("AM", era.getAsText(millis));
            assertEquals("AM", era.getAsShortText(millis));
            
            // test date
            assertEquals(expectedYear, yearValue);
            assertEquals(expectedYear, yearOfEraValue);
            assertEquals(expectedMonth, monthValue);
            assertEquals(expectedDay, dayValue);
            assertEquals(expectedDOW, dowValue);
            assertEquals(expectedDOY, doyValue);
            
            // test leap year
            assertEquals(yearValue % 4 == 3, year.isLeap(millis));
            
            // test month length
            if (monthValue == 13) {
                assertEquals(yearValue % 4 == 3, monthOfYear.isLeap(millis));
                if (yearValue % 4 == 3) {
                    assertEquals(6, monthLen);
                } else {
                    assertEquals(5, monthLen);
                }
            } else {
                assertEquals(30, monthLen);
            }
            
            // recalculate date
            expectedDOW = (((expectedDOW + 1) - 1) % 7) + 1;
            expectedDay++;
            expectedDOY++;
            if (expectedDay == 31 && expectedMonth < 13) {
                expectedDay = 1;
                expectedMonth++;
            } else if (expectedMonth == 13) {
                if (expectedYear % 4 == 3 && expectedDay == 7) {
                    expectedDay = 1;
                    expectedMonth = 1;
                    expectedYear++;
                    expectedDOY = 1;
                } else if (expectedYear % 4 != 3 && expectedDay == 6) {
                    expectedDay = 1;
                    expectedMonth = 1;
                    expectedYear++;
                    expectedDOY = 1;
                }
            }
            millis += SKIP;
        }
    }

    public void testSampleDate() {
        DateTime dt = new DateTime(2004, 6, 9, 0, 0, 0, 0, ISO_UTC).withChronology(COPTIC_UTC);
        assertEquals(CopticChronology.AM, dt.getEra());
        assertEquals(18, dt.getCenturyOfEra());  // TODO confirm
        assertEquals(20, dt.getYearOfCentury());
        assertEquals(1720, dt.getYear());
        assertEquals(1720, dt.getYearOfEra());
        assertEquals(10, dt.getMonthOfYear());
        assertEquals(2, dt.getDayOfMonth());
        assertEquals(DateTimeConstants.WEDNESDAY, dt.getDayOfWeek());
        assertEquals(9 * 30 + 2, dt.getDayOfYear());
        assertEquals(0, dt.getHourOfDay());
        assertEquals(0, dt.getMinuteOfHour());
        assertEquals(0, dt.getSecondOfMinute());
        assertEquals(0, dt.getMillisOfSecond());
    }

    public void testSampleDateWithZone() {
        DateTime dt = new DateTime(2004, 6, 9, 12, 0, 0, 0, PARIS).withChronology(COPTIC_UTC);
        assertEquals(CopticChronology.AM, dt.getEra());
        assertEquals(1720, dt.getYear());
        assertEquals(1720, dt.getYearOfEra());
        assertEquals(10, dt.getMonthOfYear());
        assertEquals(2, dt.getDayOfMonth());
        assertEquals(10, dt.getHourOfDay());  // PARIS is UTC+2 in summer (12-2=10)
        assertEquals(0, dt.getMinuteOfHour());
        assertEquals(0, dt.getSecondOfMinute());
        assertEquals(0, dt.getMillisOfSecond());
    }

}
