import cv2
import numpy as np
import socket
import base64

# 开启ip和端口
ip_port = ('192.168.0.106', 7999)
# 生成一个句柄
sk = socket.socket()
# 绑定ip端口
sk.bind(ip_port)
# 最多连接数
sk.listen(5)
# 开启死循环
print('server waiting...')
# 等待链接,阻塞，直到渠道链接 conn打开一个新的对象 专门给当前链接的客户端 addr是ip地址
conn, addr = sk.accept()
# 获取客户端请求数据
cap = cv2.VideoCapture(0)
while(1):
    # get a frame
    ret, frame = cap.read()
    # show a frame
    cv2.imshow("capture", frame)
    cv2.imwrite("temp.jpg", frame)
    # 向对方发送数据
    f = open(r'temp.jpg', 'rb')  # 二进制方式打开图文件
    ls_f = base64.b64encode(f.read())  # 读取文件内容，转换为base64编码
    print(ls_f)
    conn.sendall(ls_f)
    conn.sendall(bytes('eof', 'utf8'))
    f.close()
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
cap.release()
cv2.destroyAllWindows()
# 关闭链接
conn.close()

