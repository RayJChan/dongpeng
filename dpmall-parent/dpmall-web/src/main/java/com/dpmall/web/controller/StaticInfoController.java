package com.dpmall.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dpmall.enums.Category;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.CategoryModel;
import com.dpmall.web.controller.form.Response;

@Controller
@RequestMapping("/static")
public class StaticInfoController {
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET,value="/getCategory")
	public Response getCategory() {
		Response response=new Response();
		List<CategoryModel> data = new ArrayList<>();
		for(Category category:Category.values()) {
			CategoryModel model=new CategoryModel();
			model.name=category;
			model.value=category.getValue();
			model.cnName=category.getCode();
			data.add(model);
		}
		response.data=data;
		response.resultCode=ErrorCode.SUCCESS;
		return response;
	}
}
