create table record(
	recordId int unsigned not null auto_increment,
	articleId int unsigned not null,
	time int unsigned default 0,
	finish int unsigned default 0,
	ratio int unsigned default 0,
	addtime datetime default now(),
	updatetime datetime default now(),
	status varchar(255) default 'normal',
	primary key(recordId),
	foreign key(articleId) references article(articleId)
);