use db_sneakers_shop
go

-- Insert, update OrderDetail
drop trigger if exists after_insert_update_OrderDetail
go
create trigger after_insert_update_OrderDetail on OrderDetail
after insert, update as
declare @idOrder int, @orderDetailCost real, @idOrderDetail int, @idProduct int, @quantity int, @realQuantity int
select  @idOrder = new.idOrder, @orderDetailCost = new.quantity*p.cost , 
		@idOrderDetail = new.id, @idProduct = new.idProduct, 
		@quantity = new.quantity, @realQuantity = p.quantity
from inserted new, product p, [Order] _order
where new.idProduct = p.id and new.idOrder = _order.id
if (@quantity <= @realQuantity)
begin
	update OrderDetail
	set cost = @orderDetailCost
	where id = @idOrderDetail

	update [Order]
	set cost = (select sum(cost) from OrderDetail where OrderDetail.idOrder = @idOrder)
	where id = @idOrder

	update Product
	set quantity = @realQuantity - @quantity
	where id = @idProduct
end
else
	raisError(N'Số lượng hàng trong kho không đủ!', 16, 1, '');
go

-- After Update Order
drop trigger if exists after_update_Order
go
create trigger after_update_Order on [Order]
after update as
declare @phoneNumber varchar(10), @totalCost real, @discount int
select @phoneNumber = new.phoneNumGuess
from inserted new
begin
	select @totalCost = sum(cost) from [Order] where phoneNumGuess = @phoneNumber
	select @discount = (CAST(@totalCost as int)/10000000)*5
	-- max discount = 0.3
	if (@discount >= 30)
		update Guess
		set discount = 30, totalCost = @totalCost
		where phoneNumber = @phoneNumber
	else
		update Guess
		set discount = @discount, totalCost = @totalCost
		where phoneNumber = @phoneNumber
end
