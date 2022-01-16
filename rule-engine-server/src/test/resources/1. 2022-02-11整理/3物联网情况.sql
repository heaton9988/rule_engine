商用车
select 
a.vc_hethm,--合同号码
i.vc_chelxh,--设备型号
h.vc_chelpp,--设备品牌
c.vc_cheph,--设备号码
c.vc_chejh,--设备车架号
cd.codename,--设备类型
c.vc_fadjh,--设备发动机号
c.VC_CHEQXH,--配置
a.dt_hetqzrq,--物联网安装日期
b.vc_gpsshebh,--有线物联网设备号
f.vc_gongysmc,--有线物联网供应商
b2.vc_gpsshebh,--无线物联网设备号
f2.vc_gongysmc,--无线物联网供应商
'物联网提供',--有线物联网状态
'物联网提供',--无线物联网状态
'物联网提供',--里程
'物联网提供',--物联网预警次数
'物联网提供'--目前预警状态
from Sy_Pm_Hetb a 
left join SY_PM_RONGZCLB c on c.vc_hetbid = a.vc_hetbid
left join Sy_op_gpsanzjlb e on e.vc_rongzclbid = c.vc_rongzclbid
left join SY_OP_GPSSHEB b on b.vc_gpsanzjlid = e.vc_gpsanzjlid and b.vc_gpssheblx = '1' 
left join sy_cm_gpsgongysb f on f.vc_gpsgongysid = b.vc_gpsgongysid
left join SY_OP_GPSSHEB b2 on b2.vc_gpsanzjlid = e.vc_gpsanzjlid and b2.vc_gpssheblx = '0'
left join sy_cm_gpsgongysb f2 on f2.vc_gpsgongysid = b2.vc_gpsgongysid
left join sy_ba_pinpb h on h.vc_pinpbid = c.vc_pinpbid
left join sy_ba_xinghb i on i.vc_xinghbid = c.vc_xinghbid
left join t_wf_code cd on a.VC_CheLLX = cd.codevalue and cd.typevalue = 'SY_VC_CHELLX'  
where a.vc_hethm = 'KFL17121749';


乘用车
select 
a.vc_hethm,--合同号码
c.VC_CheXZW,--设备型号
c.vc_pinpzw,--设备品牌
h.VC_SHIFXNY,--是否新能源
c.vc_cheph,--设备号码
c.vc_chejh,--设备车架号
cd.codename,--设备类型
c.vc_fadjh,--设备发动机号
c.VC_CheXZW,--配置
a.dt_hetqzrq,--物联网安装日期
t.VC_SHIFSGPS,--是否双物联网
b2.vc_gpsshebh,--有线物联网设备号
f2.vc_gongysmc,--有线物联网供应商
b.vc_gpsshebh,--无线物联网设备号
f.vc_gongysmc,--无线物联网供应商
'物联网提供',--有线物联网状态
'物联网提供',--无线物联网状态
'物联网提供',--里程
'物联网提供',--物联网预警次数
'物联网提供'--目前预警状态
from Cy_Pm_Hetb a 
left join CY_PM_RONGZCLB c on c.vc_hetbid = a.vc_hetbid
left join cy_op_gpsanzjlb aj  on aj.vc_rongzclbid = c.vc_rongzclbid
left join CY_OP_GPSSHEBB b on b.vc_gpsanzjlbid = aj.vc_gpsanzjlbid and b.vc_gpssheblx ='0'
left join Cy_cm_gpsgongysb f on f.vc_gpsgongysbid = b.vc_gpsgongysbid
left join CY_OP_GPSSHEBB b2 on b2.vc_gpsanzjlbid = aj.vc_gpsanzjlbid and b2.vc_gpssheblx ='1'
left join Cy_cm_gpsgongysb f2 on f2.vc_gpsgongysbid = b2.vc_gpsgongysbid
left join cy_pm_xiangmxxb h on h.vc_xiangmxxbid = a.vc_xiangmxxbid
left join LB_DAILY_CONTRACT_LIST t on a.vc_hethm = t.contract_num and t.ceate_date = '2021-12-27'
left join t_wf_code cd on a.vc_chellx = cd.codevalue and cd.typevalue = 'CY_CHELLX'  
where a.vc_hethm = 'KCG16001292';