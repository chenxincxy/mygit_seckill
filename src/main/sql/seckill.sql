--秒杀执行存储过程--
DElIMITER $$ --console;转化为$$

--定义存储过程
--in 输入参数 out输出参数
--row_count（）函数 返回上一条修改类型的sql的影响行数
--由select执行 select 函数 into 变量，指把函数返回值赋给一个变量
--row_count返回结果 0：未修改，<0：sql错误/未修改，>0修改影响行数

		CREATE PROCEDURE seckill.execute_seckill
		(in v_seckill_id bigint,in v_phone bigint,
		in v_kill_time timestamp ,out r_result int)
		BEGIN
		DECLARE insert_count int DEFAULT 0;
		START TRANSACTION;
		insert ignore into success_killed
		  (seckill_id,user_phone,create_time)
		  values(v_seckill_id,v_phone,v_kill_time);
		select ROW_COUNT() into insert_count;
		IF(insert_count=0) THEN
		  ROLLBACK;
		  set r_result=-2;
		ELSEIF(insert_count<0) THEN
		   ROLLBACK;
		   set r_result=-3;
		ELSE
		  update seckill
		  set number =number -1
		  where seckill_id=v_seckill_id
		      and end_time>v_kill_time
		      and start_time<v_kill_time
		      and number>0;
		   select ROW_COUNT() into insert_count;
		   IF(insert_count=0) THEN
		      ROLLBACK;
		      set r_result=-1;
		   ELSEIF(insert_count<0) THEN
		      ROLLBACK;
		      set r_result=-3;
		   ELSE
		      COMMIT;
		      set r_result=1;
		   END IF;
		END IF;
		END;
		$$
--存储过程定义结束
--调用存储过程

--定义变量

DELIMITER ;

set @r_result=-3;
--执行
call execute_seckill(1003,12345678910,now(),@r_result);
--获取结果

select @r_result;



