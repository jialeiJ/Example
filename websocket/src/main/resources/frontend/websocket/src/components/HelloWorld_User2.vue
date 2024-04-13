<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>USER2客户端发送的信息：{{ reqData }}</p>
    <p>USER2客户端接受的信息：{{ resData }}</p>
    <button @click="user2submit">USER2发送信息</button>

    <button @click="submit">群发信息</button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'HelloWorldUser2',
  props: {
    msg: String
  },
  data () {
    return {
      reqData: null,
      resData: null,
      websock: null,
    }
  },
  mounted() {
    let that = this
    that.initWebSocket();
  },
  destroyed() {
    let that = this
    that.websock.close() //离开路由之后断开websocket连接
  },
  methods: {
    initWebSocket(){ //初始化weosocket
      let that = this
      const wsuri = "ws://127.0.0.1:8089/websocket/{2}";
      that.websock = new WebSocket(wsuri);
      that.websock.onmessage = that.websocketonmessage;
      that.websock.onopen = that.websocketonopen;
      that.websock.onerror = that.websocketonerror;
      that.websock.onclose = that.websocketclose;
    },
    websocketonopen(){ //连接建立之后执行send方法发送数据
      let that = this
      let actions = {"test":"USER2"};
      that.reqData = JSON.stringify(actions)
      that.websocketsend(JSON.stringify(actions));
    },
    websocketonerror(){//连接建立失败重连
      let that = this
      that.initWebSocket();
    },
    websocketonmessage(e){ //数据接收
      let that = this
      const data = JSON.parse(e.data);
      console.log('data', data);
      that.resData = data;
    },
    websocketsend(Data){//数据发送
      let that = this
      that.websock.send(Data);
    },
    websocketclose(e){  //关闭
      console.log('断开连接',e);
    },
    user2submit() {
      let params = {}
      //'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'
      // axios.post('http://localhost:8089/websocket/send/2',params,{
      //   headers: {
      //       'Content-Type':'application/json'
      //     }
      // }).then(res=>{
      //   console.log("res：", res)
      // },err =>{
      //   console.log(err)
      // })


      //'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'
      axios.get('http://localhost:8089/websocket/send/2', {params},{
        headers: {
            'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'
          }
      }).then(res=>{
        console.log("res：", res)
      },err =>{
        console.log(err)
      })
    },

    submit() {
      let params = {}
      //'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'
      axios.get('http://localhost:8089/websocket/send/null', {params},{
        headers: {
            'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'
          }
      }).then(res=>{
        console.log("res：", res)
      },err =>{
        console.log(err)
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
