
import com.liuyitao.common.MyFixedList;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

/***
 *@Author: liuyitao
 *@CreateDate:10:44 AM 2/4/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public class MyFixedListTest extends BaseTest {

    @Test
    public void test()
    {
        MyFixedList<Integer> list=new MyFixedList<>(100);
        IntStream.range(0,100).forEach(list::add);
        Assert.assertTrue(list.getFirst().equals(0));
        Assert.assertTrue(list.getLast().equals(99));
        list.remove(99);
        Assert.assertTrue(list.getLast()==null);

        list.add(0);
        list.add(1);

        Assert.assertTrue(list.getFirst()==1);
        Assert.assertTrue(list.getLast()==1);


    }

}
