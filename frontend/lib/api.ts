const API="/backend";

export function token(){return typeof window==="undefined"?null:localStorage.getItem("shopdue_token");}

export async function api(path:string,init:RequestInit={}){
  const headers=new Headers(init.headers);
  headers.set("Content-Type","application/json");
  const t=token();
  if(t) headers.set("Authorization","Bearer "+t);
  const res=await fetch(API+path,{...init,headers});
  if(res.status===401&&typeof window!=="undefined"){
    localStorage.removeItem("shopdue_token");
    localStorage.removeItem("shopdue_user");
    window.location.href="/login";
  }
  return res;
}

export async function apiJson<T>(path:string,init:RequestInit={}):Promise<T>{
  const r=await api(path,init);
  if(!r.ok) throw new Error((await r.text())||"Request failed");
  return r.json();
}
