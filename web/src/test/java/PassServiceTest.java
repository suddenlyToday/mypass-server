import com.liuyitao.entity.Pass;
import com.liuyitao.service.PassService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/***
 *@Author: liuyitao
 *@CreateDate:11:39 PM 1/28/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
@Rollback
@Transactional
public class PassServiceTest extends BaseTest{

    @Autowired
    private PassService passService;

    @Test
    public void test()
    {
        Pass pass=new Pass();
        pass.setWeburl("liuyitao");
        pass.setRemark("liuyitao test");
        pass.setName("aliyun");
        pass.setUsername("186");
        pass.setPassword("5211314");
        passService.add(pass);

        Pass pass1=passService.findAll().stream().filter(p->p.getUsername().equals("186")).collect(Collectors.toList()).get(0);

        Assert.assertEquals(pass.getWeburl(),pass1.getWeburl());

        passService.update(pass1);

        passService.delete(pass1.getId());

    }


}
