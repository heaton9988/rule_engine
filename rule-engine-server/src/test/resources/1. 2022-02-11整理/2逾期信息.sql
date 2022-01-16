-- 商用车
select h.vc_hethm,--合同号
contract_stat,--合同状态
cpl_lease_term, --租赁期数
(select sum(s.dec_yisfje) from sy_fm_shoufkb s where s.vc_xianjlxm in ('100009') and s.vc_hetbid = h.vc_hetbid), --已还租金
hadnub, --已还期数
cpl_lease_term-hadnub, --剩余期数
remrent, --剩余租金
baddays_sum, --逾期天数
badrent, --逾期租金
remprincipal, --剩余本金
remfxck, --风险敞口
badnub, --逾期期数
(select sum(s.dec_baozjdkje) from sy_fm_shoufkb s where s.vc_xianjlxm in ('100005','100021') and s.vc_hetbid = h.vc_hetbid), --保证金已冲抵金额
(select sum(s.dec_yisfje-s.dec_baozjdkje) from sy_fm_shoufkb s where s.vc_xianjlxm in ('100005','100021') and s.vc_hetbid = h.vc_hetbid), --租赁保证金余额
cpl_interest_penalty_apr, --逾期利率
punishpenalty, --逾期罚息
'', --罚息减免
dt_hetdqrq, --租赁约定终止日
decode(cwhx.vc_hetbid,null,'否','是'),  --是否财务核销
cwhx.dt_chuangjsj, --财务核销日期
decode(zczr.vc_hetbid,null,'否','是'), --是否转让
zczr.dt_chuangjsj, --转让日期
decode(h.vc_hetztm,'10','是','否'),  --是否财务入库
case when h.vc_hetztm='10' then cwrk.dt_chuangjsj else null end --财务入库日期
 from sy_pm_hetb h
 inner join RPT_CONTRACT_INFO t on h.vc_hethm = t.cci_contract_num and to_char(t.his_date,'yyyy-MM-dd')='2022-01-10' --注意日期
 left join hengyun.gy_pm_caiwhtztb cwhx on h.vc_hetbid = cwhx.vc_hetbid
 left join hengyun.sy_op_ziczrqdb zczr on h.vc_hetbid = zczr.vc_hetbid
 left join hengyun.gy_op_caiwrkxqb cwrk on h.vc_hetbid = cwrk.vc_hetbid
 where t.cci_contract_num = 'KFL21011980';


--乘用车
select 
h.vc_hethm, --合同号
CONTRACT_STATUS,--合同状态
t.lessee_periods, --租赁期数
(select sum(s.dec_yisfje) from cy_fm_shoufkb s where s.vc_xianjlxm in ('100009') and s.vc_waijid = h.vc_hetbid), --已还租金
REPAY_PERIODS, --已还期数
NOT_EXPIRED_PERIODS, --剩余期数
RENT_BALANCE, --剩余租金
OVERDUE_DAYS, --逾期天数
OVERDUE_RENT, --逾期租金
PRICIPAL_BALANCE, --剩余本金
RISK_EXPOSURE, --风险敞口
OVERDUE_PERIODS, --逾期期数
(select sum(s.dec_baozjdkje) from cy_fm_shoufkb s where s.vc_xianjlxm in ('100005','100021') and s.vc_waijid = h.vc_hetbid), --保证金已冲抵金额
LESSE_DEPOSIT_BALANCE, --租赁保证金余额
OVERDUE_RATE, --逾期利率
OVERDUE_PENALTY, --逾期罚息
REMIT_PENALTY, --罚息减免
dt_hetdqrq, --租赁约定终止日
decode(cwhx.vc_hetbid,null,'否','是'),  --是否财务核销
cwhx.dt_chuangjsj, --财务核销日期
decode(zczr.vc_hetbid,null,'否','是'), --是否转让
zczr.dt_chuangjsj, --转让日期
decode(h.vc_hetztm,'10','是','否'),  --是否财务入库
case when h.vc_hetztm='10' then cwrk.dt_chuangjsj else null end --财务入库日期
 from cy_pm_hetb h
 inner join LB_DAILY_CONTRACT_LIST t on h.vc_hethm = t.contract_num and t.ceate_date='2022-01-10' --注意日期
 left join hengyuncar.gy_pm_caiwhtztb cwhx on h.vc_hetbid = cwhx.vc_hetbid
 left join hengyuncar.cy_op_ziczrqdb zczr on h.vc_hetbid = zczr.vc_hetbid
 left join hengyuncar.gy_op_caiwrkxqb cwrk on h.vc_hetbid = cwrk.vc_hetbid
 where t.contract_num = 'KCG18259746';
 
 -- 乘用、商用资产转让系统 同正常系统sql