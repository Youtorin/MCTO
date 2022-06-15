
let OSS = require('ali-oss')
export let client = new OSS({
    region: 'oss-cn-hangzhou',//节点
    accessKeyId: 'accessKeyId',
    accessKeySecret: 'accessKeySecret',
    bucket: 'bucket'
})
