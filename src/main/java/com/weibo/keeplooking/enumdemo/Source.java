package com.weibo.keeplooking.enumdemo;

/**
 * All enums implicitly extend java.lang.Enum. </br>
 * 
 * Java requires that the constants be defined first, prior to any fields or
 * methods. Also, when there are fields and methods, the list of enum constants
 * must end with a semicolon. <br/>
 * 
 * The constructor for an enum type must be package-private or private access.
 * It automatically creates the constants that are defined at the beginning of
 * the enum body. You cannot invoke an enum constructor yourself. <br/>
 * 
 * The enum class body can include methods and other fields. The compiler
 * automatically adds some special methods when it creates an enum. For example,
 * they have a static values method that returns an array containing all of the
 * values of the enum in the order they are declared. This method is commonly
 * used in combination with the for-each construct to iterate over the values of
 * an enum type. For example, this code from the Planet class example below
 * iterates over all the planets in the solar system. <br/>
 * 
 * @author Johnny
 *
 */
public enum Source { // final class actually

    PC_WWW(1, "www"), // static Source member
    MOBILE_ANDROID(2, "android"), //
    MOBILE_IOS(3, "ios"), //
    MOBILE_TOUCH(4, "touch") { // tricky but OK
        @Override
        public String toString() {
            return String.format("code=%s, desc=%s", getCode(), getDesc());
        }
    };

    private int code;
    private String desc;

    private Source(int code, String desc) { // should be private
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
