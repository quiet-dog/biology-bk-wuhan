-- 更新报警信息表
UPDATE manage_emergency_event e
JOIN (
  SELECT 
    r.emergency_event_id,
    MAX(a.level) AS level
  FROM manage_emergency_event_alarm r
  JOIN manage_emergency_alarm a 
    ON r.emergency_alarm_id = a.emergency_alarm_id 
  GROUP BY r.emergency_event_id  -- 每个 event 取任意一个 alarm level
) tmp
ON e.emergency_event_id  = tmp.emergency_event_id
SET e.level = tmp.level;
