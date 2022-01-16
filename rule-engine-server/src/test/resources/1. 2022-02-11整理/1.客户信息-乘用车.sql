select 
'线下' vc_bum, -- 部门
dcl.vc_daq, -- 区域
dcl.vc_quy, -- 办事处
dcl.SALES_MANAGER, -- 客户经理
ht.vc_hethm, -- 合同号
c1.codename vc_hetgs, --合同归属
dcl.lessee_name, --承租人 
dcl.lessee_age, --承租人年龄
dcl.lessee_id_no, -- 承租人身份证
dcl.lessee_mp, -- 承租人手机号
czrsf.vc_shengfmc, -- 承租人省份
htgr.vc_jiatzz, -- 承租人地址
htgrpo.vc_germc, -- 配偶名称
htgrpo.vc_shouj, -- 配偶手机
'无' vc_danbrlx, -- 担保类型
'' vc_germc1, -- 担保人1姓名
'' vc_shouj1, -- 担保人1手机
'' vc_germc2, -- 担保人2姓名
'' vc_shouj2, -- 担保人2手机
'' vc_germc3, -- 担保人3姓名
'' vc_shouj3, -- 担保人3手机
'' vc_germc4, -- 担保人4姓名
'' vc_shouj4, -- 担保人4手机
'' vc_germc5, -- 担保人5姓名
'' vc_shouj5, -- 担保人5手机
'' vc_germc6, -- 担保人6姓名
'' vc_shouj6, -- 担保人6手机
'' vc_hexgxqtlxfs, -- 核实更新其他联系方式
yhk.vc_yinhzh, -- 还款借记卡账户
dcl.lessee_credit_rating, -- 承租人信审评级
decode(ht.vc_yewfl, '0', '乘用车',  '直转租') vc_yewlx, -- 业务类型
'线下提供' vc_chanplx, -- 产品类型
cpxx.vc_chanpmc, -- 产品方案
rzxx.dec_shebje, -- 设备金额
rzxx.dec_peijfjesx, -- 加融配件费
rzxx.dec_baoxf, -- 加融保险
rzxx.dec_gouzs, -- 加融购置税
rzxx.dec_shijsfje, -- 综合首付
rzxx.dec_zujgsbj, -- 融资金额
rzxx.dec_baozjje, -- 保证金
rzxx.dec_shijirr, -- IRR
rzxx.dec_jingrze, -- 净融资额
'无' dec_hansGP, -- 含税GP
rzxx.dec_gp, -- 税后GP
'' dec_qudf, -- 渠道费
'' dec_changszxfwf, -- 厂商咨询服务费
'' dec_changssxf, -- 厂商手续费
nvl(rzxx.dec_tiexfwf,0) dec_tiexfwf, -- 贴息服务费
decode(ht.vc_yewfl, '0', '乘用车',  '直转租') vc_yewfl2, -- 租赁类型
'' vc_lilfdlx, -- 利率浮动类型
'' dt_tiaoxjzr,-- 调息基准日
rzxx.vc_shijrzqx, -- 租期
ht.dt_hetqzrq, -- 起租日期
ht.dt_hetdqrq, -- 结束日期
dcl.REPAY_DATE, -- 当月计划还款日
decode(rzxx.vc_huankfs, '0' ,'月付期初', '月付期末') repay_method, -- 还租方式
rzxx.DEC_SHIJZJZHHS, -- 租金总额
'' vc_jiaoxxxf, -- 交叉销售方
dcl.agent, -- 代理商
dcl.agent_province, -- 代理商所属省份
dcl.agent_status, -- 代理商在线状态
dcl.apply_status, -- 申请提报标识
gk.vc_gongsmc vc_guaksmc, -- 车牌方名称
xs.vc_xinszymc, -- 信审专员
xs.vc_gaojxszymc, -- 高级信审专员
case when xmxx.vc_shifdh = '1' then '是' else '否' end as vc_shifdh -- 是否电核
from cy_pm_hetb ht 
inner join LB_DAILY_CONTRACT_LIST dcl on ht.vc_hethm = dcl.contract_num and dcl.ceate_date='2021-11-25' --注意日期
left join CY_CM_HETGRGFB htgr on htgr.vc_hetbid = ht.vc_hetbid and htgr.vc_zhongl = '0'
left join CY_CM_HETGRGFB htgrpo on htgrpo.vc_hetbid = ht.vc_hetbid and htgrpo.vc_zhongl = '1'
left join gy_cm_yinhkb yhk on yhk.vc_zhongdyhbid = ht.vc_zhongdyhbid 
left join cy_pm_chanpxxb cpxx on cpxx.vc_chanpxxbid = ht.vc_chanpxxbid 
left join cy_pm_rongzxxb rzxx on rzxx.vc_hetbid = ht.vc_hetbid 
left join Cy_pm_xiangmxsxxb xmxx on xmxx.vc_xiangmxxbid = ht.vc_xiangmxxbid 
left join t_wf_code c1 on c1.codevalue = ht.vc_hetgs and c1.typevalue = 'VC_HETGS'
LEFT JOIN cy_pm_xiangmxxb xm on ht.vc_xiangmxxbid = xm.vc_xiangmxxbid
left join cy_kz_xinsmc xs on xm.vc_shenqbh = xs.vc_shenqbh
left join BA_SHENGZXSXX czrsf on czrsf.vc_id = htgr.vc_shengf
left join cy_cm_guaksb gk on gk.vc_guaksbid = ht.vc_guaksid 
where ht.vc_hetztm in ('1', '2', '4', '5', '6', '7', '10', '13');

-- 担保人
select 
hg.vc_germc,  --  担保人姓名
hg.vc_shouj  -- 担保人手机
from cy_pm_hetb ht join cy_cm_hetgrgfb hg on ht.vc_hetbid = hg.vc_hetbid and hg.vc_zhongl = '2'
where ht.vc_hethm = '';

-- 核实更新其他联系方式
select 
c.codename, -- 关系
vc_chengzrxm_r,-- 客户姓名
vc_dianhhm   -- 电话号码
from ( 
         select ht.vc_hethm,ht.vc_chengzrxm_r,grgf.vc_shouj vc_dianhhm,grgf.vc_beiz,username,grgf.dt_chuangjsj, grgf.VC_ZhongL
         from CY_PM_HETB ht join CY_CM_HETGRGFB grgf on ht.vc_hetbid=grgf.vc_hetbid 
         left join CY_OP_CUISRWFPB rw on ht.vc_hetbid=rw.vc_hetbid  
         left join sys_user u on u.userid=rw.vc_dangqdcry 
         union  
         select ht.vc_hethm,ht.vc_chengzrxm_r,grgf.vc_shouj vc_dianhhm,grgf.vc_beiz,username,grgf.dt_chuangjsj, grgf.VC_ZhongL
         from CY_PM_HETB ht join CY_CM_HETGRGFB_HIS grgf on ht.vc_hetbid=grgf.vc_hetbid 
         left join CY_OP_CUISRWFPB rw on ht.vc_hetbid=rw.vc_hetbid  
         left join sys_user u on u.userid=rw.vc_dangqdcry 
) a 
left join t_wf_code c on a.vc_zhongl = c.codevalue and c.typevalue = 'VC_YuCZRGX'
where a.vc_hethm = '';