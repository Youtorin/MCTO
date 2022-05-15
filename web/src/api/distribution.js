import { post, get } from "@/plugins/ajax";


export const GetDis = () => get(
    "https://restapi.amap.com/v3/direction/driving?origin=116.45925,39.910031&destination=116.587922,40.081577&output=xml&key=<c6d216dc513164225218cbcd82f270ad>");