package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.ImportExcelUtils;
import com.dongpeng.common.utils.ReflectionsUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.UserMenu;
import com.dongpeng.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseDataController<MenuService,Menu> {
   /* @Autowired
    private MenuService service;*/

    /**
     * 分页查找菜单列表
     * @param menu 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(Menu menu, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        if(1==pageNo){
            return ResponseResult.ok(service.findPage(new Page<Menu>(pageNo, pageSize), menu));
        }else{
            return ResponseResult.ok(service.findPage(new Page<Menu>(pageNo, pageSize,-1), menu));
        }

    }

    /**
     * 不分页查找菜单列表
     * @param menu 封装查询参数
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult findAll(Menu menu){
        return ResponseResult.ok(service.findList(menu));
    }

    /**
     * 获取单个菜单，根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 新增一个菜单
     * @param menu 封装菜单数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Menu menu, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, menu)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        Menu menuTemp=service.getByMenuCode(menu.getMenuCode());
        if(null!=menuTemp){
            return ResponseResult.failByParam("菜单编码已存在");
        }

        int rs=service.save(menu);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存菜单失败");
        }
    }

    /**
     * 更新一个菜单信息
     * @param menu 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @EnableDetailLog(serviceclass = MenuService.class,handleName = "更新一个菜单信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody Menu menu, HttpServletRequest request, Model model) throws Exception {
        if(null==menu.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        Menu menuTemp=service.get(menu.getId());

        if(null==menuTemp){
            return ResponseResult.failByParam("id 无效");
        }

        //新编号和旧编号不相等情况下，要判断新编号是否会重复
        if(!menuTemp.getMenuCode().equals(menu.getMenuCode())){
            Menu menuByCode=service.getByMenuCode(menu.getMenuCode());
            if(null!=menuByCode){
                return ResponseResult.failByParam("菜单编码已存在");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(menu, menuTemp);//将menu非NULL值覆盖menuTemp中的值

        int rs=service.save(menuTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            //return ResponseResult.failByBusiness("更新菜单失败");
            return checkVersion(menuTemp);
        }*/
    }


    /**
     * 停用/启用 一个菜单
     * <p>实际为逻辑删除</p>
     * @param id 菜单id
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new Menu(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"启用菜单失败":"停用菜单失败");
        }
    }

    /**
     * 物理删除一个菜单
     * @param id 菜单id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new Menu(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除菜单失败");
        }
    }

    /**
     * 批量物理删除菜单
     * @param ids 菜单id集合，多个用逗号分隔
     * @return
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new Menu(Long.valueOf(id)));
        }
        return ResponseResult.ok(null);
    }

    /**
     * 删除角色所有菜单
     * @param roleId 角色id
     * @return
     */
    @RequestMapping(value = "/deleteRoleMenu/{roleId}", method = RequestMethod.POST)
    public ResponseResult deleteRoleMenu(@PathVariable("roleId") Long roleId){
        int rs=service.deleteRoleMenu(roleId);
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除角色所有菜单失败");
        }
    }

    /**
     * 插入角色菜单
     *@param roleId 角色id
     *@param menuIds 角色菜单id集合，多个用英文逗号分隔
     *@param request 请求
     * @return
     */
    @RequestMapping(value = "/insertRoleMenu", method = RequestMethod.POST)
    public ResponseResult insertRoleMenu(@RequestParam(required = true)Long roleId
            ,@RequestParam(required = true)String menuIds
            ,HttpServletRequest request){
        int rs=service.insertRoleMenu(roleId,menuIds);
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("插入角色菜单失败");
        }
    }

    /**
     * 根据角色id查找菜单信息
     * @param roleId 角色id
     * @return
     */
    @RequestMapping(value = "/listByRole/{roleId}", method = RequestMethod.GET)
    public ResponseResult findListByRoleId(@PathVariable("roleId") Long roleId){
        return ResponseResult.ok(service.findListByRoleId(roleId));
    }

    /**
     * 删除用户所有菜单
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/deleteUserMenu/{userId}", method = RequestMethod.POST)
    public ResponseResult deleteUserMenu(@PathVariable("userId") Long userId){
        int rs=service.deleteUserMenu(new UserMenu(userId));
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除用户所有菜单失败");
        }
    }

    /**
     * 插入用户菜单
     *@param userId 用户id
     *@param menuIds 角色菜单id集合，多个用英文逗号分隔
     *@param request 请求
     * @return
     */
    //@RequestMapping(value = "/insertUserMenu", method = RequestMethod.POST)
    public ResponseResult insertUserMenu(@RequestParam(required = true)Long userId
            ,@RequestParam(required = true)String menuIds
            ,HttpServletRequest request){
        int rs=service.insertUserMenu(userId,menuIds);
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("插入用户菜单失败");
        }
    }

    /**
     * 插入用户菜单
     * @param userMenus 用户菜单列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertUserMenu", method = RequestMethod.POST)
    public ResponseResult insertUserMenu(@RequestBody List<UserMenu> userMenus,HttpServletRequest request){
        int rs=service.insertUserMenu(userMenus);
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("插入用户菜单失败");
        }
    }

    /**
     * 根据用户id查找菜单信息
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listByUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListByUserId(@PathVariable("userId") Long userId){
        return ResponseResult.ok(service.findListByUserId(userId));
    }

    /**
     * 导入Excel数据
     * @param file 客户端上传的Excel文件
     * @return
     */
    @RequestMapping(value = "/import", method=RequestMethod.POST)
    public ResponseResult importFile(MultipartFile file) {
        try {
            String dataTableName=new Menu().getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);

            List<Menu> list = ei.getDataList(Menu.class);
            //####### 批量插入 ############
            for(int i=0;i<list.size();i++){
                try{
                    int rs=service.saveForExcel(list.get(i));
                    if(1<=rs){
                        successNum+=1;
                    }else{
                        failureMsg.append(" 第"+(i+1)+"个导入出错 ");
                    }
                }catch (Exception e){
                    logger.error("excel导入出错,第"+(i+1)+"个: ", e.getCause());
                    failureMsg.append(" 第"+(i+1)+"个导入出错 ");
                }
            }
            failureNum=list.size()-successNum;

            if (failureNum>0){
                failureMsg.insert(0, "，失败 "+failureNum+" 条"+dataTableName+"记录。");
            }
            return ResponseResult.ok("已成功导入 "+successNum+" 条"+dataTableName+"记录"+failureMsg);
        } catch (Exception e) {
            logger.error("导入失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导入失败！失败信息："+e.getMessage());
        }
    }
}
