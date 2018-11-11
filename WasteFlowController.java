package com.c503.hthj.asoco.dangerchemical.waste.controller;


import com.c503.hthj.asoco.dangerchemical.waste.base.JsonResult;
import com.c503.hthj.asoco.dangerchemical.waste.base.PageInfo;
import com.c503.hthj.asoco.dangerchemical.waste.controller.vo.EnterpriseInfoVO;
import com.c503.hthj.asoco.dangerchemical.waste.controller.vo.WasteDisposalCountVO;
import com.c503.hthj.asoco.dangerchemical.waste.entity.ElectronicCouplet;
import com.c503.hthj.asoco.dangerchemical.waste.entity.Enterprise;
import com.c503.hthj.asoco.dangerchemical.waste.entity.WasteDisposal;
import com.c503.hthj.asoco.dangerchemical.waste.service.ElectronicCoupletService;
import com.c503.hthj.asoco.dangerchemical.waste.service.EnterpriseService;
import com.c503.hthj.asoco.dangerchemical.waste.service.WasteDisposalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 危险废物流向信息 前端控制器
 * </p>
 *
 * @author huangr
 * @since 2018-09-29
 */
@Api(tags = "工业危险废物转运处置情况统计")
@RestController
@RequestMapping("api/wasteFlow")
public class WasteFlowController {

    @Autowired
    private ElectronicCoupletService electronicCoupletService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private WasteDisposalService wasteDisposalService;



    //废物产生流向统计信息
    @ApiOperation("查询港区所有产废企业的废物流向统计")
    @GetMapping("/queryAllWasteFlow")
    public JsonResult queryAllWasteFlow(
                            @ApiParam(name = "startDate", value = "开始日期")
                            @RequestParam(required = false) String startDate,
                            @ApiParam(name = "endDate",value = "结束时间")
                            @RequestParam(required = false) String endDate,
                            @ApiParam(name = "pageIndex",value = "当前页")
                            @RequestParam(value = "pageIndex",required = false,defaultValue = "1") int pageIndex,
                            @ApiParam(name = "pageSize",value = "当月显示数量")
                            @RequestParam(value = "pageSize",required = false,defaultValue = "10000") int pageSize ) throws  Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse(startDate);
        Date date2 = sdf.parse(endDate);
        Long l = date2.getTime() - date1.getTime();
        Long l1 = l / (1000 * 60 * 60 * 24);
        Integer ii = l1.intValue();
//        if (0<i){
//            //调用查询工具1，以天进行统计
//        }else if (31<i){
//            //调用查询工具1，以月进行统计
//        }else if (366<i){
//            //调用查询工具1，以年进行统计
//        }
        PageInfo<ElectronicCouplet> eci = electronicCoupletService.queryAllByDate(date1,date2,pageIndex, pageSize);
        List<ElectronicCouplet> list = eci.getList();//取出电子联单

        //先做31天以内的数据
        HashMap<String,HashMap<String,ArrayList<EnterpriseInfoVO>>> map=new HashMap<>();//按照月份，分别存放四个区域的数据，
        for (int i=0;i<list.size();i++) {
            //取出产废信息
            ElectronicCouplet ect = list.get(i);
            System.out.println(ect);
            //判断废物流向地点  和  处置企业信息（企业名称和处置量）
            String dest = ect.getDestination();//获取废物流向地点
            WasteDisposal wadl = wasteDisposalService.queryWasteDisposalByCoupletId(ect.getId());//获取对应的处置企业
            if (wadl==null) {
                continue;
            }
            Enterprise enterprise = enterpriseService.selectById(wadl.getEnterpriseId());
            String enterpriseName = enterprise.getEnterpriseName();//废物处置企业名称
            BigDecimal arca = wadl.getActualReceivingCapacity();//获取对应的处置企业 要处置的数量
//"1月份":{"港区":{["A企业"，7单，98.8吨],["B企业"，5单，23.5吨],["C企业"，23S单，67.8吨]},
//         "市区":{["A2企业"，7单，98.8吨],["B2企业"，5单，23.5吨],["C2企业"，23S单，67.8吨]},
//         "省内":{["A2企业"，7单，98.8吨],["B2企业"，5单，23.5吨],["C2企业"，23S单，67.8吨]},
//         "省外":{["A2企业"，7单，98.8吨],["B2企业"，5单，23.5吨],["C2企业"，23S单，67.8吨]}}
            //断点
            //判断日期
            //首先获得转运日期
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date transferTime = ect.getTransferTime();
            String s3 = df.format(transferTime).substring(0, 8);
            //String s3 = transferTime.toString().substring(4, 11);
            HashMap<String, ArrayList<EnterpriseInfoVO>> map1 = map.get(s3);
            //检查对应的月份在最外边的集合是都存在，不存在就新建集合存入数据在放入；存在就直接获取数据
            int count = 0;
            BigDecimal weight = new BigDecimal("0.0000");
            if (map1 == null) {
                count++;
                //如果map1为空，直接把值设置进去（就是某个月份的数据不存在，直接创建集合将数据保存）
                //创建一个新集合，进行保存数据
                HashMap<String, ArrayList<EnterpriseInfoVO>> map2 = new HashMap<>();
                ArrayList<EnterpriseInfoVO> lis = new ArrayList<>();
                EnterpriseInfoVO e = new EnterpriseInfoVO();
                e.setEnterpriseName(enterpriseName);
                e.setCount(count);
                weight = weight.add(arca);
                e.setWeight(weight);
                lis.add(e);//具体处置单位信息
                map2.put(dest, lis);//属于某个区域的信息
                map.put(s3, map2);//某个月份的信息

            } else {
                //每个日期都存在了
                //如果map1不为空，把值旧值取出来，再加上本次处置单位的数据，再把新值设置进去
                ArrayList<EnterpriseInfoVO> infoVOS = map1.get(dest);
                //当某月的某一个区域不存在时，新建集合往里面保存数据
                if ((map1.size() != 4) && (!map1.containsKey(dest))) {
                    count++;
                    weight = weight.add(arca);
                    ArrayList<EnterpriseInfoVO> infoV = new ArrayList<>();
                    EnterpriseInfoVO evo = new EnterpriseInfoVO();
                    evo.setEnterpriseName(enterpriseName);
                    evo.setCount(count);
                    evo.setWeight(weight);
                    infoV.add(evo);
                    map1.put(dest, infoV);//新的容器必须放进去
                    map.put(s3, map1);
                } else {
                    //每个区域的数据都存在了
                    //当某月的某一个区域的数据存在时，继续往集合里放入数据
                    Integer ff = 0;
                    for (EnterpriseInfoVO evo : infoVOS) {
                        String enterpriseName1 = evo.getEnterpriseName();
                        //如果港区里的统计包含了这个企业，取出旧的量，增加新的量，再把值set进去，
                        if (enterpriseName1.equals(enterpriseName)) {//是同一个企业的信息
                            ff++;
                            Integer count1 = evo.getCount();
                            count1++;
                            evo.setCount(count1);
                            BigDecimal weight1 = evo.getWeight();
                            weight1 = weight1.add(arca);
                            evo.setWeight(weight1);
                            break;
                        }
                    }
                    //不是同一个企业的信息进行保存
                    //如果不是同一个企业的信息，等循环遍历结束后再放入集合中，不然一边循环一边插入数据，容易引发java.util.ConcurrentModificationException: null
                    if (ff == 0) {
                        EnterpriseInfoVO evvo = new EnterpriseInfoVO();
                        evvo.setEnterpriseName(enterpriseName);
                        count++;
                        evvo.setCount(count);
                        weight = weight.add(arca);
                        evvo.setWeight(weight);
                        infoVOS.add(evvo);
                    }
                }
            }
        }//for循环ElectronicCouplet
        //@SuppressWarnings("unchecked")
        List<HashMap<String,ArrayList<EnterpriseInfoVO>>> list1 = new ArrayList<>();
        for (int i= 0;i<list.size();i++) {
            ElectronicCouplet ele = list.get(i);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String s = df.format(ele.getTransferTime()).substring(0, 8);//转移日期
            HashMap<String, ArrayList<EnterpriseInfoVO>> hashMap = map.get(s);
            boolean b = list1.contains(hashMap);
            Integer cd=0;
            if (b==false){
                list1.add(cd,hashMap);
                cd++;
            }
        }
//        for (int i= 0;i<list1.size();i++ ) {
//            HashMap<String, ArrayList<EnterpriseInfoVO>> listHashMap = list1.get(i);
//            System.out.println(listHashMap.toString());
//        }
        return new JsonResult(0,"success",list1);
    }


    /**
     * 查询港区所有处置企业的废物处置情况
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation("查询港区所有处置企业的废物处置情况")
    @GetMapping("/queryAllDisposalCount")
    public JsonResult queryAllDisposalCount(
                            @ApiParam(name = "pageIndex",value = "当前页")
                            @RequestParam(value = "pageIndex",required = false,defaultValue = "1") int pageIndex,
                            @ApiParam(name = "pageSize",value = "当页显示数量")
                            @RequestParam(value = "pageSize",required = false,defaultValue = "1000") int pageSize){
        PageInfo<WasteDisposal> pageInfo = wasteDisposalService.queryAllDisposalCount(pageIndex,pageSize);
        List<WasteDisposal> list = pageInfo.getList();
        List<WasteDisposalCountVO> list1 = queryy(list);

        PageInfo<WasteDisposalCountVO> pageInfo1= new PageInfo<>();
        pageInfo1.setList(list1);//没有封装页码页数，就两个数据
        return new JsonResult(0,"success",pageInfo1);
    }

    //查询废物处置情况统计工具
    public List<WasteDisposalCountVO> queryy(List<WasteDisposal> list){
        //存放数据
        List<WasteDisposalCountVO> list1 = new ArrayList<>();
        //定义变量
        Integer count1=0;//count1表示港区内产废处置的单数
        BigDecimal AAA = new BigDecimal("0.0000" );//AAA表示港区内产废处置的吨数
        Integer count2=0;//count2表示港区内外产废处置的单数
        BigDecimal BBB = new BigDecimal("0.0000" );//BBB表示港区外产废处置的吨数

        Map<String,String> map = new HashMap<>();
        for (WasteDisposal wd: list) {
            //废物处置量
            BigDecimal arca = wd.getActualReceivingCapacity();
            //获取产废企业名称
            ElectronicCouplet electronicCouplet = electronicCoupletService.selectById(wd.getCoupletId());
            //从电子联单获取的企业名称肯定存在
            String producingEnterprise = electronicCouplet.getProducingEnterprise();
            Long id = electronicCouplet.getProducingUnitId();

            Enterprise enterprise = enterpriseService.selectById(id);
            //根据联单上企业id查询所得
            String enterpriseName = enterprise.getEnterpriseName();
            if((id!=null) && (producingEnterprise.equals(enterpriseName))){
                String s1 = map.get("港区");
                //如果不存在
                if (s1==null){
                    count1++;
                    AAA=AAA.add(arca);
                    String ss1=count1+"-"+AAA;
                    map.put("港区",ss1);
                }else {
                    //如果已经存在
                    String[] split = s1.split("-");
                    //统计单数
                    count1 = Integer.valueOf(split[0]);
                    count1++;
                    //已经累加的值
                    AAA=new BigDecimal(split[1]);
                    AAA = AAA.add(arca);
                    String sss1=count1+"-"+AAA;
                    map.put("港区",sss1);
                }
            }else {
                String s2 = map.get("港区外");
                if (s2==null){
                    count2++;
                    BBB=BBB.add(arca);
                    String ss2=count2+"-"+BBB;
                    map.put("港区外",ss2);
                }else {
                    String[] split = s2.split("-");
                    //统计单数
                    count2 = Integer.valueOf(split[0]);
                    count2++;
                    //已经累加的值
                    BBB=new BigDecimal(split[1]);
                    BBB = BBB.add(arca);
                    String sss2=count2+"-"+BBB;
                    map.put("港区外",sss2);
                }
            }
        }
        Integer c= count1+count2;//总单数
        BigDecimal DDD = AAA.add(BBB);//总处理数
        Set<String> set = map.keySet();
        for (String s:set) {
            WasteDisposalCountVO wdc = new WasteDisposalCountVO();
            //区域名称
            wdc.setRegion(s);
            String s1 = map.get(s);
            String[] split = s1.split("-");
            Integer integer = Integer.valueOf(split[0]);
            wdc.setCount(integer+"单");
            //取百分比
            double v = (double) integer / c;
            //获取格式化对象
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度2即保留两位小数
            nt.setMinimumFractionDigits(2);
            String format = nt.format(v);
            wdc.setPercent(format);
            //处置量
            BigDecimal CCC = new BigDecimal(split[1]);
            wdc.setDisposalWeight(CCC+"吨");

            double vv = CCC.divide(DDD, 6, BigDecimal.ROUND_HALF_UP).doubleValue();
            String format1 = nt.format(vv);
            wdc.setPercent2(format1);

            list1.add(wdc);//放入集合
        }
        return list1;
    }

    /**
     * 根据处置企业名称查询该企业所有废物处置情况
     * @param enterpriseName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation("根据处置企业名称查询该企业所有废物处置情况")
    @GetMapping("/queryDisposalCountByEnterpriseName/{enterpriseName}")
    public JsonResult queryDisposalCountByEnterpriseName(
            @ApiParam(name = "enterpriseName",value = "企业名称",required = true)
            @NotEmpty(message = "企业名称不能为空") @PathVariable("enterpriseName") String enterpriseName,
            @ApiParam(name = "pageIndex",value = "当前页")
            @RequestParam(value = "pageIndex",required = false,defaultValue = "1") int pageIndex,
            @ApiParam(name = "pageSize",value = "当页数量",required = true)
            @RequestParam(value = "pageSize",required = false,defaultValue = "1000") int pageSize){
        PageInfo<WasteDisposal> pageInfo =
                wasteDisposalService.queryDisposalCountByEnterpriseName(enterpriseName,pageIndex,pageSize);
        List<WasteDisposal> list = pageInfo.getList();
        List<WasteDisposalCountVO> queryy = queryy(list);

        PageInfo<WasteDisposalCountVO> pageInfo1= new PageInfo<>();
        pageInfo1.setList(queryy);//没有封装页码页数，就两个数据
        return new JsonResult(0,"success",pageInfo1);
    }


}

