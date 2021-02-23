Object.defineProperty(Object.prototype, 'deepClone', {
    value(){
        const result = this instanceof Array ? [] : {};
        for(let key in this){
            if(this[key] instanceof Date){
                result[key] = new Date(+this[key]);
            }else if(typeof this[key] === 'object' && (this[key] !== null) && this[key].deepClone){
                result[key] = this[key].deepClone();
            }else{
                result[key] = this[key];
            }
        }
        return result;
    }
})