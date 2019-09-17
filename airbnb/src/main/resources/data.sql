------ INSERT user
INSERT INTO airbnb_user ( email
                    , name
                    , password
                    , whitepin_token)
VALUES ( 'admin'
       , 'admin'
       , '$2a$10$Q8cgIoqLvufVIkYZLzfi7O6rRi9eNn2/18APAmzRbW9rsA921MJuG'
       , 'aa'
       )
ON DUPLICATE KEY
UPDATE
    airbnb_user.email = airbnb_user.email
    , airbnb_user.name = airbnb_user.name
    , airbnb_user.password = airbnb_user.password
    , airbnb_user.whitepin_token = airbnb_user.whitepin_token;

------ INSERT product
INSERT INTO airbnb_product ( info
                    , name
                    , user_id)
VALUES ( 'test info'
       , 'grape'
       , '1'
       )
ON DUPLICATE KEY
UPDATE
    airbnb_product.info = airbnb_product.info
    , airbnb_product.name = airbnb_product.name
    , airbnb_product.user_id = airbnb_product.user_id;
	
------ INSERT trade
INSERT INTO airbnb_trade ( buyer_user_id
                    , seller_user_id
                    , product_buy_status
                    , whitepin_trade_id
					, trade_date
					, evaluation_score1
					, evaluation_score2
					, evaluation_score3
					, evaluation_score4
					, evaluation_score5
					, evaluation_score_total
					, evaluation_review
					, whitepin_score_key
					, evaluation_date
					, product_id)
VALUES ( '1'
       , '2'
       , '0001'
       , '1'
	   , null
	   , '1'
	   , '1'
	   , '1'
	   , '1'
	   , '1'
	   , '1'
	   , '1'
	   , '1'
	   , null	
	   , '1'	   
       )
ON DUPLICATE KEY
UPDATE
    airbnb_trade.buyer_user_id = airbnb_trade.buyer_user_id
    , airbnb_trade.seller_user_id = airbnb_trade.seller_user_id
    , airbnb_trade.product_buy_status = airbnb_trade.product_buy_status
    , airbnb_trade.whitepin_trade_id = airbnb_trade.whitepin_trade_id
	, airbnb_trade.trade_date = airbnb_trade.trade_date
	, airbnb_trade.evaluation_score1 = airbnb_trade.evaluation_score1
	, airbnb_trade.evaluation_score2 = airbnb_trade.evaluation_score2
	, airbnb_trade.evaluation_score3 = airbnb_trade.evaluation_score3
	, airbnb_trade.evaluation_score4 = airbnb_trade.evaluation_score4
	, airbnb_trade.evaluation_score5 = airbnb_trade.evaluation_score5
	, airbnb_trade.evaluation_score_total = airbnb_trade.evaluation_score_total
	, airbnb_trade.evaluation_review = airbnb_trade.evaluation_review
	, airbnb_trade.whitepin_score_key = airbnb_trade.whitepin_score_key
	, airbnb_trade.evaluation_date = airbnb_trade.evaluation_date
	, airbnb_trade.product_id = airbnb_trade.product_id;