# Socket

### 场景描述
socket通信：

# 一、Socket服务启动方式一

    @SpringBootApplication
    public class SocketApplication {
    
        public static void main(String[] args) {
            ApplicationContext applicationContext = SpringApplication.run(SocketApplication.class, args);
            //在spring容器启动后，取到已经初始化的SocketServer，启动Socket服务
            //applicationContext.getBean(SocketServer.class).start();
        }
    
    }

# 一、Socket服务启动方式二
    
    @Slf4j
    @Data
    @Component
    @PropertySource("classpath:socket.properties")
    @NoArgsConstructor
    public class SocketServer implements CommandLineRunner {
        @Value("${socket.port}")
        private Integer port;
        private boolean started;
        private ServerSocket serverSocket;
        private ExecutorService executorService = Executors.newCachedThreadPool();
    
        public void start(Integer port){
            log.info("port: {}, {}", this.port, port);
            try {
                serverSocket =  new ServerSocket(port == null ? this.port : port);
                started = true;
                log.info("Socket服务已启动，占用端口： {}", serverSocket.getLocalPort());
            } catch (IOException e) {
                log.error("端口冲突,异常信息：{}", e);
                System.exit(0);
            }
    
            while (started){
                try {
                    Socket socket = serverSocket.accept();
                    socket.setKeepAlive(true);
                    ClientSocket register = register(socket);
                    log.info("客户端已连接，其Key值为：{}", register.getKey());
                    List<String> list = new ArrayList<>();
                    list.add("111");
                    list.add("222");
                    list.add("3334");
                    SocketHandler.sendMessage(register, JSON.toJSONString(list));
                    if (register != null){
                        executorService.submit(register);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    
        /**
         * 启动
         * @param args
         * @throws Exception
         */
        @Override
        public void run(String... args) throws Exception {
            start(8068);
        }
    }
    
# 发送数据问题

    如果发送数据过大时，发送方会对数据进行分包发送，这种情况下或导致接收方判断错误，误以为数据传输完成，因而接收不全。
    在这种情况下就会引出一些问题，诸如半包，粘包，分包等问题。
        1、半包
            接受方没有接受到一个完整的包，只接受了部分。
            原因：TCP为提高传输效率，将一个包分配的足够大，导致接受方并不能一次接受完。
            影响：长连接和短连接中都会出现
        2、粘包
            发送方发送的多个包数据到接收方接收时粘成一个包，从接收缓冲区看，后一包数据的头紧接着前一包数据的尾。
            分类：一种是粘在一起的包都是完整的数据包，另一种情况是粘在一起的包有不完整的包
            出现粘包现象的原因是多方面的:
                1)发送方粘包：由TCP协议本身造成的，TCP为提高传输效率，发送方往往要收集到足够多的数据后才发送一包数据。若连续几次发送的数据都很少，
                    通常TCP会根据优化算法把这些数据合成一包后一次发送出去，这样接收方就收到了粘包数据。
                2)接收方粘包：接收方用户进程不及时接收数据，从而导致粘包现象。这是因为接收方先把收到的数据放在系统接收缓冲区，
                    用户进程从该缓冲区取数据，若下一包数据到达时前一包数据尚未被用户进程取走，则下一包数据放到系统接收缓冲区时就接到前一包数据之后，而用户进程根据预先设定的缓冲区大小从系统接收缓冲区取数据，这样就一次取到了多包数据。
        3、分包
            分包（1）：在出现粘包的时候，我们的接收方要进行分包处理；
            分包（2）：一个数据包被分成了多次接收；
            原因：
                1. IP分片传输导致的；
                2. 传输过程中丢失部分包导致出现的半包；
                3. 一个包可能被分成了两次传输，在取数据的时候，先取到了一部分（还可能与接收的缓冲区大小有关系）。
            影响：粘包和分包在长连接中都会出现
          
            那么如何解决半包和粘包的问题，就涉及一个一个数据发送如何标识结束的问题，通常有以下几种情况
                固定长度：每次发送固定长度的数据；
                特殊标示：以回车，换行作为特殊标示；获取到指定的标识时，说明包获取完整。
                字节长度：包头+包长+包体的协议形式，当服务器端获取到指定的包长时才说明获取完整；
                所以大部分情况下，双方使用socket通讯时都会约定一个定长头放在传输数据的最前端，用以标识数据体的长度，
                通常定长头有整型int，短整型short，字符串Strinng三种形式。
    
