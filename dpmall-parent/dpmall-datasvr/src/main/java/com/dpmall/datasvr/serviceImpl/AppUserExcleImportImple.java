package com.dpmall.datasvr.serviceImpl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dpmall.common.CglibBeanCopierUtils;
import com.dpmall.datasvr.api.IAppUserExcleImportService;
import com.dpmall.db.bean.AppGroupEntity;
import com.dpmall.db.bean.AppUser_NewEntity;
import com.dpmall.db.bean.KeHuEntity;
import com.dpmall.db.dao.UserGroupDao;
import com.dpmall.model.AppGroupModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "appUserExcleImportService")
public class AppUserExcleImportImple implements IAppUserExcleImportService {
    public static void main(String[] args) throws Exception {
       List<AppGroupModel> list = readXls();
        System.out.println(JSON.toJSONString(list));

    }


    @Autowired
    private UserGroupDao userGroupDao;

    /**
     * 获取appgroupModle
     */
    public static List<AppGroupModel> readXls() {
        List<AppGroupModel> list = new ArrayList<AppGroupModel>();
        InputStream is = null;
        try {

            is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\appUsers.xls");
            HSSFWorkbook excel = new HSSFWorkbook(is);


            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < excel.getNumberOfSheets(); numSheet++) {
                HSSFSheet sheet = excel.getSheetAt(numSheet);
                if (sheet == null)
                    continue;


                // 循环行Row(内容)
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    HSSFRow row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    AppGroupModel groupModel = new AppGroupModel();
                    //Code
                    HSSFCell cell0 = row.getCell(0);
                    if (cell0 == null )  {
                        continue;
                    }
                    cell0.setCellType(Cell.CELL_TYPE_STRING);//转类型
                    if (StringUtils.isEmpty(cell0.getStringCellValue())){
                        continue;
                    }
                    groupModel.setCode(cell0.getStringCellValue());

                    //Name
                    HSSFCell cell1 = row.getCell(1);
                    if (cell1 == null)  {
                        continue;
                    }
                    groupModel.setName(cell1.getStringCellValue().trim());

                    //Type
                    HSSFCell cell2 = row.getCell(2);
                    if (cell2 == null)  {
                        continue;
                    }
                    groupModel.setType(cell2.getStringCellValue().trim());

                    //ParentCode
                    HSSFCell cell3 = row.getCell(3);
                    if (cell3 != null) {
                        cell3.setCellType(Cell.CELL_TYPE_STRING);
                        groupModel.setParentCode(cell3.getStringCellValue());
                    }

                    list.add(groupModel);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return list;
    }

    @SuppressWarnings("unused")
    private static String getValue(HSSFCell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型 值
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            //返回数值类型的值
            return String.valueOf(cell.getNumericCellValue());
        } else {
            //返回字符串类型的值
            return cell.getStringCellValue();
        }
    }


    /**
     * 添加app信息
     */
    @Transactional
    public void addApp() {
        List<AppGroupModel> list = readXls();
        if (list == null) {
            return;
        }
        List<AppGroupEntity> entities = new ArrayList<>();
       StringBuffer buffer = new StringBuffer();
        for (AppGroupModel model : list) {

            //1、查询appgroup是否已经有值,有值跳过
            AppGroupEntity groupEntity = userGroupDao.getByCode(model.code);
            if (groupEntity != null ){
                continue;
            }else{
                groupEntity = new AppGroupEntity();
            }
            CglibBeanCopierUtils.copyProperties(model,groupEntity);


            //父类信息
            if (!"0".equals(groupEntity.parentCode)) {
                AppGroupEntity parentEntity = userGroupDao.getByCode(groupEntity.parentCode);
                if (parentEntity != null) {
                    groupEntity.setParentId(parentEntity.getId());//父类id
                    //所有父类id
                    buffer.append(parentEntity.getId());
                    buffer.append(",");
                    if (parentEntity.getParentIdList()!=null){
                        buffer.append(parentEntity.getParentIdList());
                    }

                    groupEntity.setParentIdList(buffer.toString());
                    buffer.setLength(0);//清空buffer
                }
            }
            //添加至数据库
            userGroupDao.insertAppGroup(groupEntity);

            groupEntity = userGroupDao.getByCode(groupEntity.code);

            //2、判断是否存在 appuser ，是：更新group_id ，否：添加
            AppUser_NewEntity appUserEntity = userGroupDao.getAppUserByUserName(model.getCode());
            if (appUserEntity != null){
                appUserEntity.setGroupId(groupEntity.getId().toString());
            }else{
                appUserEntity  = new AppUser_NewEntity();
                appUserEntity.setCnName(groupEntity.getName());
                appUserEntity.setPassword("14e1b600b1fd579f47433b88e8d85291");
                appUserEntity.setRole(groupEntity.getType());
                appUserEntity.setUserName(groupEntity.getCode());
                appUserEntity.setGroupId(groupEntity.getId().toString());
                //查询客户id、orgID
                KeHuEntity keHuEntity =  userGroupDao.getKeHu(groupEntity.getCode());
                if (keHuEntity !=null && (keHuEntity.getId() !=null || StringUtils.isNotEmpty(keHuEntity.getOrgId()))){
                    appUserEntity.setKehuId(keHuEntity.getId());
                    appUserEntity.setOrgId(Long.valueOf( keHuEntity.getOrgId()));
                }

                userGroupDao.isnertAppUser(appUserEntity);
            }






        }
    }

}
