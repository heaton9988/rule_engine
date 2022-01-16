select 
'线下' vc_bum, -- 部门
ci.io_parent_name, -- 区域
ci.io_name, -- 办事处
u.username vc_zhubxsjl, -- 客户经理
ht.vc_hethm, -- 合同号
c1.codename vc_hetgs, --合同归属
htgr.vc_germc, --承租人 
ci.cust_age, --承租人年龄
htgr.vc_zhengjhm, -- 承租人身份证
htgr.vc_shouj, -- 承租人手机号
htgr.vc_jiatzz, -- 承租人省份
htgr.vc_jiatzz, -- 承租人地址
htgrpo.vc_germc, -- 配偶名称
htgrpo.vc_shouj, -- 配偶手机
'' vc_danbrlx, -- 担保类型
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
'' lessee_credit_rating, -- 承租人信审评级
'商用车' vc_yewlx, -- 业务类型
'线下提供' vc_chanplx, -- 产品类型
'' vc_chanpmc, -- 产品方案
rzxx.dec_shebje, -- 设备金额
'' dec_peijfjesx, -- 加融配件费
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
'' dec_tiexfwf, -- 贴息服务费
'回租' vc_zullx, -- 租赁类型
'' vc_lilfdlx, -- 利率浮动类型
'' dt_tiaoxjzr,-- 调息基准日
rzxx.vc_shijrzqx, -- 租期
ht.dt_hetqzrq, -- 起租日期
ht.dt_hetdqrq, -- 结束日期
ci.cpl_refund_mode, -- 还租方式
ci.dayplan,  -- 当月计划还款日
rzxx.DEC_SHIJZJZHHS, -- 租金总额
jcxs.vc_mingc vc_jiaocxsf, -- 交叉销售方
case when xm.vc_changstx='CSTX_07' then kpjxsb.vc_jingxsqc when xm.vc_changstx='CSTX_12' then kpjxsb.vc_jingxsqc
else jingxsb.vc_jingxsqc end vc_jingxsmc, -- 代理商
'' agent_province, -- 代理商所属省份
'' agent_status, -- 代理商在线状态
'' apply_status, -- 申请提报标识
gk.vc_guaksmc vc_guaksmc, -- 车牌方名称
'' participant_name1, -- 信审专员
'' participant_name2, -- 高级信审专员
'' vc_shifdh -- 是否电核
from sy_pm_hetb ht 
inner join RPT_CONTRACT_INFO ci on ht.vc_hethm = ci.cci_contract_num and to_char(ci.his_date,'yyyy-MM-dd')='2021-11-25' --注意日期
left join sy_pm_xiaosxmsmb xsm on xsm.vc_xiangmxxbid = ht.vc_xiangmxxbid
left join SY_CM_HETGRGFB htgr on htgr.vc_hetbid = ht.vc_hetbid and htgr.vc_zhongl = '0'
left join SY_CM_HETGRGFB htgrpo on htgrpo.vc_hetbid = ht.vc_hetbid and htgrpo.vc_zhongl = '1'
left join gy_cm_yinhkb yhk on yhk.vc_zhongdyhbid = ht.vc_zhongdyhbid 
left join sy_pm_rongzxxb rzxx on rzxx.vc_hetbid = ht.vc_hetbid 
left join t_wf_code c1 on c1.codevalue = ht.vc_hetgs2 and c1.typevalue = 'SY_VC_HeTGS'
LEFT JOIN sy_pm_xiangmxxb xm  ON ht.vc_xiangmxxbid = xm.vc_xiangmxxbid
left join sy_cm_jingxsb kpjxsb on kpjxsb.vc_jingxsbid = xm.VC_KAIPJXSID
left join sy_cm_jingxsb jingxsb on jingxsb.vc_jingxsbid = xm.VC_JINGXSBID
left join bz_jingxsjcxs jxsjcxs on jxsjcxs.vc_jingxs = ht.vc_jingxsbid
left join sy_cm_guaksb gk on gk.vc_guaksbid = ht.vc_guaksbid
LEFT JOIN bz_hetjcxs htjcxs ON htjcxs.vc_hetbh = ht.vc_hetbid 
LEFT JOIN ba_jiaocxsf jcxs ON jcxs.vc_zhi = HTJCXS.VC_JIAOCXSF
left join SY_CM_YuanGXX yuang on xsm.vc_zhubxsjl=yuang.vc_yuangxxid
left join sys_user u ON u.userid = yuang.userid
where ht.vc_hetztm in ('1', '2', '4', '5', '6', '7', '10', '13');

-- 担保人
select 
hg.vc_germc,  --  担保人姓名
hg.vc_shouj  -- 担保人手机
from sy_pm_hetb ht join sy_cm_hetgrgfb hg on ht.vc_hetbid = hg.vc_hetbid and hg.vc_zhongl = '2'
where ht.vc_hethm = '';