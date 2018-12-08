### ==和equals

	==和！=既适用于基本数据类型，又适用于引用数据类型；

	用于基本数据类型比较值，用于引用数据类型比较引用（内用地址），引用数据类型要想比较内容用equals方法，equals方法不适用基本数据类型。

	String的equals方法：

```
public boolean equals(Object anObject) {
    if (this == anObject) {  
    //this指当前对象，anObject传入该方法的对象，对象用==比较比的是内存地址；
    //如果两个对象内存地址一样，就是自己和自己比较；
        return true;
    }
    if (anObject instanceof String) {
        String anotherString = (String)anObject;
        int n = value.length;
        if (n == anotherString.value.length) {
            char v1[] = value;
            char v2[] = anotherString.value;
            int i = 0;
            while (n-- != 0) {
                if (v1[i] != v2[i])
                    return false;
                i++;
            }
            return true;
        }
    }
    return false;
}
测试效果如下：
public class Student3 {

    public static void main(String[] args) {
        String aa = "aa";
        String bb = "bb";
        System.out.println(aa.equals(bb));//false
        System.out.println(aa.equals(aa));//true

    }

}
```