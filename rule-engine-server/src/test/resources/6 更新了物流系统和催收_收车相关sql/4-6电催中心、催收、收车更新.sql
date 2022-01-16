--电催中心
--乘用
select VC_HETHM             as 合同号码,
       cy.VC_DANGQDCRY      as 电催对应人员,
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
                   on cy.VC_HETBID = ht.VC_HETBID
inner join CY_PM_HETB het on het.VC_HETBID = cy.VC_HETBID where VC_HETHM = ?;

--催收
--乘用
select aa.VC_HETHM as 合同号码,gs.VC_GONGSMC as 催收公司, aa.催收模式, aa.委外时间, aa.委外开始日, aa.委外结束日, aa.催收开始日, aa.催收结束日,op.vc_yuqzt as 客户状态
from (
         select '委外'              as 催收模式,
                cy.VC_CUISWBGSBID as 催收公司,
                cy.VC_WEIANQI     as 委外时间,
                cy.DT_WEIANKSR    as 委外开始日,
                cy.DT_WEIANDQR    as 委外结束日,
                cy.DT_PDANDATE    as 催收开始日,
                cy.DT_WEIANDQR    as 催收结束日,
                cy.VC_HETHM,
                cy.VC_HETBID,

                                     row_number() over (partition by vc_hetbid order by dt_chuangjsj desc) rn
         from CY_OP_CUISWBPDJLB cy) aa
         left join CY_CM_CUISWBGSB gs on aa.催收公司 = gs.VC_CUISWBGSBID
         left join (select c.vc_hetbid,
                           c.vc_yuqzt,
                           row_number() over (partition by c.vc_hetbid order by c.dt_chuangjsj desc) conunt
                    from cy_op_dianhcsjlb c) op
                   on aa.VC_HETBID = op.VC_HETBID  and conunt = 1
where rn = 1
  and VC_HETHM = ?;
--商用 -- 可能无数据     可能无数据
select aa.VC_HETHM as 合同号码,gs.VC_GONGSMC as 催收公司, aa.催收模式, aa.委外时间, aa.委外开始日, aa.委外结束日, aa.催收开始日, aa.催收结束日,op.vc_yuqzt as 客户状态
from (
         select '委外'              as 催收模式,
                sy.VC_CUISWBGSBID as 催收公司,
                sy.VC_WEIANQI     as 委外时间,
                sy.DT_WEIANKSR    as 委外开始日,
                sy.DT_WEIANDQR    as 委外结束日,
                sy.DT_PDANDATE    as 催收开始日,
                sy.DT_WEIANDQR    as 催收结束日,
                sy.VC_HETHM,
                sy.VC_HETBID,

                                     row_number() over (partition by vc_hetbid order by dt_chuangjsj desc) rn
         from sy_OP_CUISWBPDJLB sy) aa
         left join sy_CM_CUISWBGSB gs on aa.催收公司 = gs.VC_CUISWBGSBID
         left join (select c.vc_hetbid,
                           c.vc_yuqzt,
                           row_number() over (partition by c.vc_hetbid order by c.dt_chuangjsj desc) conunt
                    from sy_op_dianhcsjlb c) op
                   on aa.VC_HETBID = op.VC_HETBID  and conunt = 1
where rn = 1
  and VC_HETHM = ?;
--收车
--乘用
select aa.VC_HETHM         as 合同号码,
       aa.dec_chelxsj      as 收回设备价格预估,
       '是'                 as 是否派单回收设备,
       bb.sum              as 派单回收次数,
       aa.participant_name as 回收公司,
       aa.create_time      as 派单回收开始日,
       aa.shouqjz          as 派单回收结束日,
       aa.tcf          as 收车费
from (
         select rownum,
                tc.*,
                row_number() over (partition by tc.VC_CHEJH order by tc.create_time desc) rn
         from (select h.vc_hethm,
                      cl.vc_chejh,
                      gp.participant_name,
                      to_char(gp.create_time, 'YYYY-MM-DD')                                                            create_time,
                      case
                          when gp.code_id != 1 and s.abc > 1 then to_char(gp.create_time, 'yyyy-mm-dd')
                          else (
                              case
                                  when fa.vc_shouqsx = '0'
                                      then to_char(gp.create_time + NUMTODSINTERVAL(15, 'day'), 'yyyy-mm-dd')
                                  when fa.vc_shouqsx = '1'
                                      then to_char(gp.create_time + NUMTODSINTERVAL(30, 'day'), 'yyyy-mm-dd')
                                  when fa.vc_shouqsx = '2'
                                      then to_char(gp.create_time + NUMTODSINTERVAL(60, 'day'), 'yyyy-mm-dd') end) end shouqjz,
                      cl.dec_chelxsj,
                      nvl(baog.dec_shouhsbfy, 0) + nvl(baog.dec_yunsfy, 0)                                             tcf
               from cy_op_zulwhsjlb_apl z
                        left join cy_pm_hetb h
                                  on h.vc_hetbid = z.vc_hetbid

                        left join cy_cm_fudlsb f on f.vc_fuwdlsbid = h.vc_fuwdlsid
                        left join cy_pm_rongzclb cl
                                  on cl.vc_hetbid = h.vc_hetbid
                        left join CY_OP_ZuLWHSFAB_APL fa
                                  on fa.vc_zulwhsjlbid = z.vc_zulwhsjlbid
                        inner join (select w.businesskey,
                                           w.ext_prop,
                                           w.create_time,
                                           w.participant_name,
                                           ROW_NUMBER()
                                                   OVER (PARTITION BY w.businesskey ORDER BY w.accept_time desc) AS code_id
                                    from wf_workitem w
                                    where w.ext_prop like '%scgssc%') gp
                                   on gp.businesskey = z.vc_pcid
                                       and gp.ext_prop like '%scgssc%'
                        inner join (select count(*) abc, wf.businesskey
                                    from wf_workitem wf
                                    where wf.ext_prop like '%scgssc%'
                                    group by wf.businesskey) s
                                   on s.businesskey = z.vc_pcid
                        left join
                    cy_op_chelhsbgb baog on baog.vc_zulwhsjlbid = z.vc_zulwhsjlbid
              ) tc) aa
         inner join (select cl.vc_chejh,
                            count(h.VC_HETHM) sum
                     from cy_op_zulwhsjlb_apl z
                              left join cy_pm_hetb h
                                        on h.vc_hetbid = z.vc_hetbid
                              left join cy_pm_rongzclb cl
                                        on cl.vc_hetbid = h.vc_hetbid
                              inner join (select w.businesskey,
                                                 w.ext_prop,
                                                 w.create_time,
                                                 w.participant_name,
                                                 ROW_NUMBER()
                                                         OVER (PARTITION BY w.businesskey ORDER BY w.accept_time desc) AS code_id
                                          from wf_workitem w
                                          where w.ext_prop like '%scgssc%') gp
                                         on gp.businesskey = z.vc_pcid
                                             and gp.ext_prop like '%scgssc%'
                              inner join (select count(*) abc, wf.businesskey
                                          from wf_workitem wf
                                          where wf.ext_prop like '%scgssc%'
                                          group by wf.businesskey) s
                                         on s.businesskey = z.vc_pcid
                     group by VC_CHEJH) bb on aa.VC_CHEJH = bb.VC_CHEJH
where rn = 1 and VC_HETHM = ''

--商用
select aa.VC_HETHM      as 合同号码,
       aa.dec_shebje    as 收回设备价格预估,
       '是'              as 是否派单回收设备,
       bb.sum           as 派单回收次数,
       aa.vc_gongsmc    as 回收公司,
       aa.complete_time as 派单回收开始日,
       aa.vc_shouqsx    as 派单回收结束日,
       aa.tcf           as 收车费
from (
         select h.vc_hethm
              , car.vc_chejh
              , to_char(p.complete_time, 'yyyy-MM-dd hh24:mi:ss')                                             complete_time
              , decode(fa.VC_SHOUQSX, null, to_char(p.complete_time + 30, 'yyyy-MM-dd hh24:mi:ss'),
                       to_char(p.complete_time + nvl(substr(c2.codename, 0, 2), 0), 'yyyy-MM-dd hh24:mi:ss')) vc_shouqsx
              , car.dec_chelxsj                                                                               dec_shebje
              , nvl(tuo.vc_gongsmc, ban.vc_banscmc)                                                           vc_gongsmc
              , nvl(baog.dec_shouhsbfy, 0) + nvl(baog.dec_yunsfy, 0)                                          tcf
              , row_number() over (partition by car.VC_CHEJH order by complete_time desc)                     rn
         from sy_op_zulwhsjlb_apl hs
                  left join sy_op_zulwhsfab_apl fa on fa.vc_zulwhsjlbid = hs.vc_zulwhsjlbid
                  left join t_wf_code c2 on c2.typevalue = 'VC_HuiSSQSX' and c2.codevalue = fa.vc_shouqsx
                  left join sy_pm_rongzclb_tmp car on car.vc_hetbid = hs.vc_hetbid and hs.vc_pcid = car.vc_pcid
                  inner join (
             select *
             from (
                      select p.business_id,
                             w.create_time,
                             w.complete_time,
                             decode(h.AUDIT_STATUS, 2, '同意', '自由驳回')                                    audit_status,
                             row_number() over (partition by p.business_id order by w.create_time desc) rown
                      from wf_process_instance p
                               left join wf_workitem w on w.proc_instance_id = p.proc_instance_id
                               left join wf_audit_history h on w.WORKITEM_INS_ID = h.WORKITEM_INS_ID
                      where p.business_id like 'HF32%'
                        and w.ext_prop like '%customId,hszxgdy%'
                        and h.AUDIT_STATUS in ('2', '4')
                  )
         ) p on p.business_id = hs.vc_pcid
                  left join sy_pm_hetb h on h.vc_hetbid = hs.vc_hetbid
                  left join sy_cm_banscb ban on ban.vc_banscbid = fa.vc_banscid
                  left join sy_cm_tuocgsb tuo on tuo.vc_tuocgsbid = fa.vc_tuocgsbid
                  left join sy_op_chelbgdb bg
                            on bg.vc_zulwhsjlbid = hs.vc_zulwhsjlbid and bg.VC_RONGZCLBID = car.VC_RONGZCLBID
                  left join
              sy_op_chelhsbgb baog
              on baog.vc_rongzclbid = bg.vc_rongzclbid and baog.vc_zulwhsjlbid = bg.vc_zulwhsjlbid) aa
         left join
     (
         select count(h.vc_hethm) sum
              , car.vc_chejh
         from sy_op_zulwhsjlb_apl hs
                  left join sy_pm_rongzclb_tmp car on car.vc_hetbid = hs.vc_hetbid and hs.vc_pcid = car.vc_pcid
                  inner join (
             select *
             from (
                      select p.business_id,
                             w.create_time,
                             w.complete_time,
                             decode(h.AUDIT_STATUS, 2, '同意', '自由驳回')                                    audit_status,
                             row_number() over (partition by p.business_id order by w.create_time desc) rown
                      from wf_process_instance p
                               left join wf_workitem w on w.proc_instance_id = p.proc_instance_id
                               left join wf_audit_history h on w.WORKITEM_INS_ID = h.WORKITEM_INS_ID
                      where p.business_id like 'HF32%'
                        and w.ext_prop like '%customId,hszxgdy%'
                        and h.AUDIT_STATUS in ('2', '4')
                  )
         ) p on p.business_id = hs.vc_pcid
                  left join sy_pm_hetb h on h.vc_hetbid = hs.vc_hetbid
         group by VC_CHEJH) bb on aa.VC_CHEJH = bb.VC_CHEJH
where aa.rn = 1 and VC_HETHM = ''