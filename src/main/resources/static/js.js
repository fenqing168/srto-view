new Vue({
    el: '#app',
    data(){
        return {
            type: 'bubbleSort',
            types: [
                {
                    value: 'bubbleSort',
                    label: '冒泡排序'
                },
                {
                    value: 'insertSort',
                    label: '插入排序'
                },
                {
                    value: 'shellSort',
                    label: '希尔排序'
                },
                {
                    value: 'selectSort',
                    label: '选择排序'
                },
                {
                    value: 'quickSort',
                    label: '快速排序'
                },
                {
                    value: 'mergeSort',
                    label: '归并排序'
                }
            ],
            len: 50,
            arr: [],
            min: 0,
            max: 100
        }
    },
    mounted(){
        this.myChart = echarts.init(this.$refs.charts);
        this.myChart.setOption(this.option);
    },
    computed:{
        option(){
            return {
                title: {
                    text: '数据可视化看板'
                },
                tooltip: {},
                legend: {
                    data:['数据']
                },
                xAxis: {
                    data: this.arr.map((item, index) => index + '')
                },
                yAxis: {},
                key: null,
                series: [{
                    label:{
                      show: this.arr.length < 100
                    },
                    name: '数据',
                    type: 'bar',
                    data: this.arr,
                    animation: false
                }]
            }
        }
    },
    methods:{
        buildArr(){
            const arr = new Array(this.len).fill(0);
            const off = this.max - this.min;
            for (let i = 0; i < arr.length; i++) {
                arr[i] = Number.parseInt(Math.random() * off + this.min)
            }
            this.arr = arr;
        },
        async startSort(){

            let webSocket = new WebSocket('ws://' + window.location.host + '/sort');
            webSocket.onopen =  () => {
                webSocket.send(JSON.stringify({
                    type: this.type,
                    arr: this.arr
                }));
            };
            webSocket.onmessage = (evt) => {
                const {arr, ok} = JSON.parse(evt.data);
                if(ok){
                    webSocket.close();
                }else{
                    this.arr = arr;
                }
            }
        },
        async getArr(){
            const {data:data} = await axios.get('/get/arr', {
                params: {
                    key: this.key
                }
            });
            this.arr = data.data.arr;
            if(!data.data.ok){
                setTimeout(() => {
                    this.getArr();
                }, 200)
            }

        }
    },
    watch: {
        option: {
            deep: true,
            handler(nv, ov){
                this.myChart.setOption(nv);
            }
        }
    }
});