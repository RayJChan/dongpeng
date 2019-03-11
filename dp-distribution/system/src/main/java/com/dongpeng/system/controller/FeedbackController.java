package com.dongpeng.system.controller;


import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.Feedback;
import com.dongpeng.system.service.FeedbackService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/feedback")
public class FeedbackController extends BaseDataController<FeedbackService,Feedback> {
    /**
     * 新增一条反馈
     * @param feedback 封装反馈数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Feedback feedback, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, feedback)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }

        int rs=service.add(feedback);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存反馈失败");
        }
    }

    /**
     * 分页查找
     * @param feedback
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list/{" + Global.PAGE_SIZE + "}/{" +Global.PAGE_NO + "}", method=RequestMethod.GET)
    public ResponseResult findPage(Feedback feedback, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseResult.ok(service.findPage(new Page<Feedback>(pageNo, pageSize), feedback));
    }

    /**
     * 根据用户id查找反馈列表
     * @param person_id 用户id
     * @return
     */
    @RequestMapping(value = "/listByPerson/{person_id}", method = RequestMethod.GET)
    public ResponseResult findListByPersonId(@PathVariable("person_id") Long person_id){
        return ResponseResult.ok(service.findListByPersonId(person_id));
    }

    /**
     * 根据内容进行模糊查询
     */
   @RequestMapping(value="/searchList", method = RequestMethod.GET)
    public ResponseResult searchList(@RequestParam(value="content", required=true) String content) {
       return ResponseResult.ok(service.searchList(content));
   }
}
