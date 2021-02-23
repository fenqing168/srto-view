Object.defineProperty(Array.prototype, 'getMin', {
    value() {
        if (!this.length) {
            return -1;
        }
        let arrmin = this[0];   // 初始化第一个值
        for (let i = 1; i < this.length; i++) {
            if (this[i] < arrmin) {
                arrmin = this[i];
            }
        }
        return arrmin;    //返回最小值
    }
})