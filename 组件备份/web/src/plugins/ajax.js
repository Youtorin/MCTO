/*
 * axios的封装
 * axios的基本配置和封装方法
 */
import axios from 'axios'

// axios 配置
axios.defaults.timeout = 20000000 // 设置超时时间200S
// 设置请求头
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
//跨域请求是否可以携带cookie
axios.defaults.withCredentials = true


// 添加响应拦截器
axios.interceptors.response.use(response => {
  // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据
  // 否则的话抛出错误
  if (response.data.success) {
    return Promise.resolve(response)
  } else {
    return Promise.reject(response)
  }
}, error => {
  // 对请求错误做些什么
  return Promise.reject(error)
})

/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function get (url, params) {
  return new Promise((resolve, reject) => {
    url = setRequestBaseUrl(url)
    axios.get(url, {
      params: params
    }).then(res => {
      resolve(res.data)
    }).catch(err => {
      reject(err.data)
    })
  })
}

/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 * @param {Object} config [请求头内容]
 */
export function post (url, params, config) {
  return new Promise((resolve, reject) => {
    url = setRequestBaseUrl(url)
    axios.post(url, params, config)
      .then(res => {
        resolve(res.data)
      })
      .catch(error => {
        if (error.data && !error.data.success) {
          return reject(error.data.error)
        } else if (error.response) { // 对响应错误做点什么
          console.log(error.response.data.message)
          return reject(error.response.data.error)
        } else {
          if (error.status !== 200) {
            if (error.message && error.message.includes('timeout')) {
              return reject({
                message: '连接超时'
              })
            } else {
              return reject({
                message: '无法获取错误信息,请确认是否连接网络!'
              })
            }
          }
        }
      })
  })
}
