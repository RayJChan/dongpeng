package com.dpmall.datasvr.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.dpmall.common.OssInfoUtils;
import com.dpmall.common.PrivateKeyUtils;
import com.dpmall.datasvr.api.IPictureService;
import com.dpmall.db.bean.PictureEntity;
import com.dpmall.db.dao.PictureDao;
import com.dpmall.enums.EPictureType;
import com.dpmall.model.PictureModel;

/**
 * 获取图片路径
 * 
 * @author cwj
 * @date 2017-12-26
 */
@Service(value = "pictureService")
public class PictureServiceImpl implements IPictureService {

	@Autowired
	private PictureDao pictureDao;
	
	/**
	 * entity转Model
	 * @param in
	 * @return
	 */
	private PictureModel entityToModel (PictureEntity in) {
		PictureModel out = new PictureModel();
		if (in !=null) {
			BeanUtils.copyProperties(in, out);
			out.setImgUrl(OssInfoUtils.getOssUrl()+in.getName());//图片访问url路径
		}
		return out;
	}
	
	/***
	 * entityList转ModelList
	 * @param in
	 * @return
	 */
	private List<PictureModel> entityListToModelList(List<PictureEntity> in){
		List<PictureModel> out = new ArrayList<PictureModel>();
		if (in == null && CollectionUtils.isEmpty(in)) {
			return out;
		}
		for(PictureEntity entity:in) {
			PictureModel model = new  PictureModel();
			model = this.entityToModel(entity);
			out.add(model);
		}
		return out;
	}
	
	public List<PictureModel> getPictures(Integer offset, Integer pageSize,String pictureType) {
		List<PictureEntity> entities = new ArrayList<PictureEntity>();
		
		//祝福卡横幅
		if (EPictureType.CARDS_GUIDE.getType().equals(pictureType)) {
			entities = pictureDao.getPicture(offset, pageSize,EPictureType.CARDS_GUIDE.getType());
		}
		//祝福卡内容
		else if (EPictureType.CARDS_CONTENT.getType().equals(pictureType)) {
			entities = pictureDao.getPicture(offset, pageSize,EPictureType.CARDS_CONTENT.getType());
		}
		List<PictureModel>out = this.entityListToModelList(entities);//转型
		return out;
	}

	public int uploadPicture(String pictureName,String title,String content,String pictureType) {
		PictureEntity entity = new PictureEntity();
		entity.setId(PrivateKeyUtils.getPkByRandom_ms());
		entity.setName(pictureName);
		entity.setContent(content);
		entity.setTitle(title);
		entity.setPictureType(pictureType);
		int result = pictureDao.uploadPicture(entity);
		return result;
	}

	

	

}
