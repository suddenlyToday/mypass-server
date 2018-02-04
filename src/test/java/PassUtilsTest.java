import com.liuyitao.common.PassEDUtil;
import org.junit.Assert;
import org.junit.Test;

/***
 *@Author: liuyitao
 *@CreateDate:10:11 PM 1/29/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public class PassUtilsTest extends BaseTest {


    @Test
    public void testDecodeAndEncode()
    {
        String encodeStr= PassEDUtil.encodePass("liuyitao");

        Assert.assertEquals("liuyitao",PassEDUtil.decodePass(encodeStr));

    }
}
