//package cn.business.main.handler;
//
//import cn.business.main.service.ReportService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//@Api(tags = "接口：报表管理和解析")
//@RestController
//public class ReportController {
//	@Resource
//	private ReportService service;
//
//	@ApiOperation(value = "解析模块-获取解析及展示报表", notes = "通过repcode获取报表信息解析公式并展示最终样式")
//	@GetMapping(value = "/report/show/{repcode}")
//	public String show(@ApiParam(required = true, name = "repcode", value = "报表编号")
//	                   @PathVariable(name = "repcode", required = true) String repcode) {
//		System.out.println("rest 风格的GET请求..........id=" + repcode);
//		return "/t/t";
//	}
//
//	@ApiOperation(value = "管理模块-新增报表", notes = "新增报表")
//	@PostMapping(value = "/report")
//	public ReportBean add() {
//		return new ReportBean();
//	}
//
//	@ApiOperation(value = "管理模块-修改报表", notes = "修改报表")
//	@PutMapping(value = "/report/{repcode}")
//	public String edit(
//			@ApiParam(required = true, name = "repcode", value = "报表编号")
//			@PathVariable(name = "repcode") String repcode) {
//		return null;
//	}
//
//	@ApiOperation(value = "管理模块-删除报表", notes = "删除报表")
//	@DeleteMapping(value = "/report/{repcode}")
//	public String del(
//			@ApiParam(required = true, name = "repcode", value = "报表编号")
//			@PathVariable(name = "repcode") String repcode) {
//		return null;
//	}
//
//	@ApiOperation(value = "管理模块-获取报表列表", notes = "获取报表列表")
//	@GetMapping(value = "/report")
//	public String list() {
//		return null;
//	}
//
//	@ApiOperation(value = "管理模块-获取报表明细", notes = "获取报表明细")
//	@GetMapping(value = "/report/{repcode}")
//	public String get(
//			@ApiParam(required = true, name = "repcode", value = "报表编号")
//			@PathVariable(name = "repcode") String repcode) {
//		return null;
//	}
//}
