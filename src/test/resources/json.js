var o = {
	model : MeishiListQueryModel,
	channelId : null,
	cityId : 1,
	districtId : null,
	areaId : null,
	catId : null,
	subCatId : null,
	shopId : null,
	minPrice : null,
	maxPrice : null,
	isReserve : null,
	isCash : null,
	offset : 0,
	limit : 120,
	sortCondition : [ {
		channelSort : ASC
	}, {
		channelScore : ASC
	}, {
		global : ASC
	}, {
		soldOut : ASC
	}, {
		actStartTime : DESC
	}, {
		goodsId : DESC
	} ],
	minPerson : null,
	maxPerson : null,
	timeFlag : 3
}