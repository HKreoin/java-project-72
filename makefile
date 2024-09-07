 .DEFAULT_GOAL := build-run

clean:
	make -C ./app clean

lint:
	make -C ./app lint

test:
	make -C ./app test

start:
	make -C ./app start

build:
	make -C ./app build

.PHONY: build


report:
	make -C ./app report