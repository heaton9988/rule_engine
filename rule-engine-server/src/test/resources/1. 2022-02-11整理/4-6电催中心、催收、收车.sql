--电催中心
--乘用
select cy.VC_DANGQDCRY      as 电催对应人员,
       cy.DT_CHUANGJSJ      as 电催开始日,
       cy.DT_CHUANGJSJ + 30 as 电催结束日,
       op.vc_yuqzt          as 客户状态,
       ht.cuissl            as 电催次数
from CY_OP_CUISRWFPB cy
         left join (select c.vc_hetbid,
                           c.vc_cuisrwfpbid,
                           c.vc_yuqzt,
                           row_number() over (partition by c.vc_hetbid,c.vc_cuisrwfpbid order by c.dt_chuangjsj desc) rn
                    from cy_op_dianhcsjlb c) op
                   on cy.VC_HETBID = op.VC_HETBID and cy.vc_cuisrwfpbid = op.vc_cuisrwfpbid and rn = 1
         left join (select VC_HETBID, count(distinct VC_CUISRY) cuissl from CY_OP_DIANHCSJLB group by VC_HETBID) ht
                   on cy.VC_HETBID = ht.VC_HETBID;
--催收
--乘用
select gs.VC_GONGSMC, aa.* from (
select '委外'              as 催收模式,
       cy.VC_CUISWBGSBID as 催收公司,
       cy.VC_WEIANQI     as 委外时间,
       cy.DT_WEIANKSR    as 委外开始日,
       cy.DT_WEIANDQR    as 委外结束日,
       cy.DT_PDANDATE    as 催收开始日,
       cy.DT_WEIANDQR    as 催收结束日,
       row_number() over (partition by vc_hetbid order by dt_chuangjsj desc) rn
from CY_OP_CUISWBPDJLB cy) aa  left join CY_CM_CUISWBGSB gs on aa.催收公司 = gs.VC_CUISWBGSBID where rn = 1;

--商用
select gs.VC_GONGSMC, aa.* from (
select '委外'              as 催收模式,
       sy.VC_CUISWBGSBID as 催收公司,
       sy.VC_WEIANQI     as 委外时间,
       sy.DT_WEIANKSR    as 委外开始日,
       sy.DT_WEIANDQR    as 委外结束日,
       sy.DT_PDANDATE    as 催收开始日,
       sy.DT_WEIANDQR    as 催收结束日,
       row_number() over (partition by vc_hetbid order by dt_chuangjsj desc) rn
from sy_OP_CUISWBPDJLB sy) aa  left join sy_CM_CUISWBGSB gs on aa.催收公司 = gs.VC_CUISWBGSBID where rn = 1;

--收车
--乘用
select rong.dec_chelxsj as 收回设备价格预估,'是' as 是否派单回收设备,派单回收次数,tuo.vc_gongsmc as 回收公司 ,派单回收开始日,派单回收结束日,nvl(baog.dec_shouhsbfy,0)+nvl(baog.dec_yunsfy,0) as 收车费 from cy_pm_rongzclb rong inner join (
select VC_RONGZCLBID,count(cy.vc_pcid) as 派单回收次数,max(wf.COMPLETE_TIME) as 派单回收开始日,max(decode( cy.VC_SHOUQSX,null,to_char(wf.complete_time + 30, 'yyyy-MM-dd hh24:mi:ss') ,to_char(wf.complete_time + nvl(substr(c2.codename, 0, 2), 0), 'yyyy-MM-dd hh24:mi:ss'))) as 派单回收结束日
from cy_op_zulwhsfab_apl cy left join wf_workitem wf on cy.VC_PCID = wf.BUSINESSKEY and wf.ext_prop like '%scgssc%'
left join t_wf_code c2 on c2.typevalue='VC_HuiSSQSX' and c2.codevalue=cy.vc_shouqsx
inner join  cy_op_zulwhsjlb_apl op on cy.VC_ZULWHSJLBID = op.VC_ZULWHSJLBID
inner join cy_pm_rongzclb_tmp ro on ro.VC_HETBID = op.VC_HETBID and ro.vc_pcid = op.vc_pcid group by VC_RONGZCLBID) aa on rong.VC_RONGZCLBID = aa.VC_RONGZCLBID
inner join cy_op_zulwhsjlb_apl bb on bb.VC_HETBID = rong.VC_HETBID
left join
    cy_op_chelhsbgb baog on baog.vc_rongzclbid=rong.vc_rongzclbid and baog.vc_zulwhsjlbid=bb.vc_zulwhsjlbid
    left join cy_op_zulwhsfab_apl fa on bb.VC_ZULWHSJLBID=fa.VC_ZULWHSJLBID
left join cy_cm_tuocgsb tuo on tuo.vc_tuocgsbid=fa.vc_tuocgsbid
left join CY_PM_HETB het on het.VC_HETBID = rong.VC_HETBID where het.VC_HETHM = ''

--商用
select rong.dec_chelxsj as 收回设备价格预估,'是' as 是否派单回收设备,派单回收次数,nvl(tuo.vc_gongsmc,ban.vc_banscmc) as 回收公司 ,派单回收开始日,派单回收结束日,nvl(baog.dec_shouhsbfy,0)+nvl(baog.dec_yunsfy,0) as 收车费 from sy_pm_rongzclb rong inner join (
select VC_RONGZCLBID,count(sy.vc_pcid) as 派单回收次数,max(wf.COMPLETE_TIME) as 派单回收开始日,max(decode( sy.VC_SHOUQSX,null,to_char(wf.complete_time + 30, 'yyyy-MM-dd hh24:mi:ss') ,to_char(wf.complete_time + nvl(substr(c2.codename, 0, 2), 0), 'yyyy-MM-dd hh24:mi:ss'))) as 派单回收结束日
from sy_op_zulwhsfab_apl sy left join wf_workitem wf on sy.VC_PCID = wf.BUSINESSKEY and wf.ext_prop = 'customId,hszxgdy'
left join t_wf_code c2 on c2.typevalue='VC_HuiSSQSX' and c2.codevalue=sy.vc_shouqsx
inner join  sy_op_zulwhsjlb_apl op on sy.VC_ZULWHSJLBID = op.VC_ZULWHSJLBID
inner join sy_pm_rongzclb_tmp ro on ro.VC_HETBID = op.VC_HETBID and ro.vc_pcid = op.vc_pcid group by VC_RONGZCLBID) aa on rong.VC_RONGZCLBID = aa.VC_RONGZCLBID
inner join sy_op_zulwhsjlb_apl bb on bb.VC_HETBID = rong.VC_HETBID
inner join
    sy_op_chelhsbgb baog on baog.vc_rongzclbid=rong.vc_rongzclbid and baog.vc_zulwhsjlbid=bb.vc_zulwhsjlbid
    left join sy_op_zulwhsfab_apl fa on bb.VC_ZULWHSJLBID=fa.VC_ZULWHSJLBID
left join sy_cm_tuocgsb tuo on tuo.vc_tuocgsbid=fa.vc_tuocgsbid
left join sy_cm_banscb ban on ban.vc_banscbid=fa.vc_banscid;