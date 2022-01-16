
-- 客户信息
select '线下'                                                                               部门,
       '区域'                                                                               无,
       s1.ORG_NAME                                                                        办事处,
       s2.USERNAME                                                                        客户经理,
       h.VC_HETBH                                                                         合同编号,
       '无'                                                                                合同归属,
       t2.VC_KEHMC                                                                        客户编号,
       '无'                                                                                承租人年龄,
       '无'                                                                                承租人身份证,
       '无'                                                                                承租人手机,
       s3.VC_SHENGFMC                                                                     承租人省份,
       '无'                                                                                承租人地址,
       '无'                                                                                配偶姓名,
       '无'                                                                                配偶手机,
       (select VC_MINGC from BA_SHUJZD where VC_ZHI = h.VC_DANBLXBQ)                      担保类型,
       '无'                                                                                核实更新其他联系方式,
       t3.VC_KAIHZH                                                                       还款借记卡账户,
       '无'                                                                                承租人信审评级,
       '现代物流'                                                                             业务类型,
       '手工添加'                                                                             产品类型,
       '无'                                                                                产品方案,
       h.DEC_BIAODWJE                                                                     设备金额,
       '无'                                                                                加融配件费,
       '无'                                                                                加融保险,
       '无'                                                                                加融购置税,
       t5.DEC_JIASHJ                                                                      综合首付,
       t4.DEC_ZULGSBJBL                                                                   融资金额,
       (select sum(j.DEC_BAOZJDK) from fm_jiaoyjgywxjl j where j.NU_JIAOYJGID = t4.NU_ID) 保证金,
       t4.DEC_SPEC_IRR                                                                    IRR,
       t4.DEC_JINGRZE                                                                     净融资额,
       t4.DEC_HANSGP                                                                      含税GP,
       t4.DEC_GP_AFTER_TAX                                                                税后GP,
       nvl(t4.XDEC_QUDFJE, 0)                                                             渠道费,
       nvl(t4.XDEC_ZIXFWFSR, 0)                                                           厂商咨询服务费,
       nvl(t4.XDEC_SHOUXF, 0)                                                             厂商手续费,
       '无'                                                                                贴息服务费,
       (select VC_MINGC from BA_SHUJZD where VC_ZHI = h.VC_ZULLXBQ)                       租赁类型,
       t4.VC_LILFDLX                                                                      利率浮动类型,
       '无'                                                                                调息基准日,
       t4.NU_ZULQX                                                                        租期,
       h.DT_YUEDQZR                                                                       起租日期,
       t7.end_date                                                                        结束日期,
       t4.NU_HUANZFS                                                                      还租方式,
       t4.NU_MEIYCFR                                                                      当月计划还款日,
       t4.DEC_ZULHTZJE                                                                    租金总额,
       t8.VC_JIAOCXSF                                                                     交叉销售方,
       t7.SALE_NAME                                                                       代理商,
       '无'                                                                                代理商所属省份,
       '无'                                                                                代理商在线状态,
       '无'                                                                                申请提报标识,
       t2.VC_KEHMC                                                                        车牌方名称,
       '无'                                                                                信审专员,
       '无'                                                                                高级信审专员,
       '无'                                                                                是否电核
from BZ_HETJBXX h
         left join BZ_YEJGS t1 on h.VC_HETBH = t1.VC_HETBH
         left join CM_JIGKH t2 on h.VC_KEHBH = t2.VC_KEHBH
         left join CM_KEHYHZH t3 on h.VC_KEHBH = t3.VC_KEHBH
         left join FM_JIAOYJG t4 on h.VC_HETBH = t4.VC_CESZTBH
         left join fm_jiaoyjgfyx t5 on t4.NU_ID = t5.NU_JIAOYJGID and t5.VC_FEIYLX = '101'
         left join rpt_contract_info t7 on h.VC_HETBH = t7.CONTRACT_ID and t7.HIS_DATE = '2022-01-10'
         left join BZ_HETJCXS t8 on h.VC_HETBH = t8.VC_HETBH
         left join SYS_ORG_INFO s1 on h.VC_XIANGMCDBM = s1.ORG_ID
         left join SYS_USER s2 on t1.VC_YEJGSYWY = s2.USERID
         left join BA_SHENGZXSXX s3 on t2.VC_SHENGF = s3.VC_ID
where h.VC_HETBH = 'KFL21100058001';

--担保人信息
select t2.VC_KEHMC,   --担保人姓名
       t1.VC_LIANXRSJ --担保人手机
from PM_XIANGMDBFS t1
         left join BZ_HETJBXX h1 on t1.VC_KEHBH = h1.VC_KEHBH
         left join CM_JIGKH t2 on t1.VC_KEHBH = t2.VC_KEHBH
where VC_HETBH = 'KFL21100058001';

--逾期信息
select 
h.VC_HETBH，                                           --  合同编号 
h.VC_HETZT,                                                 --合同状态
       t1.NU_ZULQX,                                                --租赁期数
       '无',                                                        --已还租金
       '无',                                                        --已还期数
       '无',                                                        --剩余期数
       '无',                                                        --剩余租金
       t2.BADDAYS,                                                 --逾期天数
       t2.BADRENT,                                                 --逾期租金
       t2.REMCAPITAL,                                              --剩余本金
       t2.REMFXCK,                                                 --风险敞口
       t2.BADNUB,                                                  --逾期期数
       '无',                                                        --保证金已冲抵金额
       '无',                                                        --租赁保证金余额
       '无',                                                        --罚息减免
       t2.end_date,                                                --租赁约定终止日
       '无',                                                        --是否财务核销
       '无',                                                        --财务核销日期
       '无',                                                        --是否转让
       '无',                                                        --转让日期
       case when h.VC_HETZT = '61' then '是' else '否' END VC_HETZT, --是否财务入库
       '无'                                                         --财务入库日期
from BZ_HETJBXX h
         left join FM_JIAOYJG t1 on h.VC_HETBH = t1.VC_CESZTBH
         left join RPT_CONTRACT_INFO t2 on h.VC_HETBH = t2.CONTRACT_ID and t2.HIS_DATE = '2022-01-10'
where h.VC_HETBH = 'KFL21100058001';