package com.lesport.webcontroller;

import java.text.DecimalFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/webtools")
public class ToolController {
	final double MINBMI = 18.5;
	final double MAXBMI = 24;

	@RequestMapping(value = "/bmitools")
	public ModelAndView bmiTools(String hei, String wei) {
		System.out.println("hei:" + hei);
		System.out.println("wei:" + wei);
		// 获取页面的mov对象
		ModelAndView modelAndView = new ModelAndView("pages/tool_BMI");
		if (hei == null || wei == null) {
			return modelAndView;
		}
		float height = Float.parseFloat(hei);// 解析为float类型的身高数值,获取到的数值为cm
		float weight = Float.parseFloat(wei);// 解析为float类型的体重数值,获取到的数值为kg
		height = height / 100;// 将cm转化为m

		float bmi = weight / height / height;// bmi指数计算公式
		DecimalFormat df = new DecimalFormat("#.00");

		String result = "";
		if (bmi < 18.5) {
			result = "偏轻";
		} else if (18.5 <= bmi && bmi < 24) {
			result = "正常";
		} else {
			result = "超重";
		}

		modelAndView.addObject("bmi", df.format(bmi));// 将计算后的bmi指数作为属性添加到mov对象中
		modelAndView.addObject("result", result);
		return modelAndView;
	}

	@RequestMapping(value = "/calculate")
	public ModelAndView calculate(String gender, String age, String hei) {
		System.out.println("gender:" + gender);

		ModelAndView modelAndView = new ModelAndView("pages/tool_calculate");
		if (hei == null || age == null) {
			return modelAndView;
		}
		double height = Double.parseDouble(hei);
		double result = 0;

		if (gender.length() == 3) {// 传递过来的性别为男
			result = (height - 80) * 0.7;
		} else {
			result = (height - 70) * 0.6;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		// 获取页面的mov对象
		modelAndView.addObject("stanard", df.format(result));
		return modelAndView;
	}

	@RequestMapping(value = "/calorie")
	public ModelAndView colarie(String gender, String age, String hei, String wei) {
		System.out.println("gender:" + gender);
		ModelAndView modelAndView = new ModelAndView("pages/tool_calorie");
		if (age == null || hei == null || wei == null) {
			return modelAndView;
		}
		double height = Double.parseDouble(hei);
		double weight = Double.parseDouble(wei);
		double ages = Double.parseDouble(age);

		double result = 0;
		if (gender.length() == 3) {// 传递过来的性别为男

			result = 67 + 13.73 * weight + 5 * height - 6.9 * ages;
		} else {
			result = 661 + 9.6 * weight + 1.72 * height - 4.7 * ages;
		}

		DecimalFormat df = new DecimalFormat("#.00");
		// 获取页面的mov对象
		modelAndView.addObject("calorie", df.format(result));
		return modelAndView;
	}

	@RequestMapping(value = "/range")
	public ModelAndView range(String hei) {

		System.out.println("hei:" + hei);
		ModelAndView modelAndView = new ModelAndView("pages/tool_range");
		if (hei == null) {
			return modelAndView;
		}
		double height = Double.parseDouble(hei);// 解析为float类型的身高数值,获取到的数值为cm
		String bmi = "";
		height = height / 100;
		double min = height * height * MINBMI;
		double max = height * height * MAXBMI;
		DecimalFormat df = new DecimalFormat("#.00");
		bmi = df.format(min) + "~" + df.format(max);
		System.out.println("min:" + min);
		System.out.println("max:" + max);
		// 获取页面的mov对象
		modelAndView.addObject("result", bmi);// 将计算后的bmi指数作为属性添加到mov对象中
		return modelAndView;
	}
}
