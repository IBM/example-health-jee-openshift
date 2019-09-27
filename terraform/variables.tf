variable "org" {
    description = "Your IBM Cloud organization which is gnerally your email address. Run `ibmcloud cf orgs` to see all your orgs."
 }

variable "space" {
    description = "Your IBM Cloud space to provision the Compose for MySQL service. Run `ibmcloud cf spaces` to see all your spaces."
}

variable "region" {
    default = "us-south"
    description = "Region to deploy your Compose MySQL instance."
 }

variable "datacenter" {
    default = "wdc04"
    description = "The datacenter to provision your Red Hat OpenShift cluster in IBM Cloud."
}

variable "machine_type" {
    default = "b2c.4x16"
    description = "The flavor of worker node in your cluster. Run `ibmcloud ks flavors --zone <datacenter>` to see the different flavors."
}

variable "private_vlan_id" {
    description = "Your private VLAN ID. If you don't have one, set this value to 'null' and one will be created for you. Run `ic ks vlans --zone <datacenter>` to see your VLANs."
 }

variable "public_vlan_id" { 
    description = "Your private VLAN ID. If you don't have one, set this value to 'null' and one will be created for you. Run `ic ks vlans --zone <datacenter>` to see your VLANs."
}

variable "cluster_name" {
    default = "cluster1"
    description = "Name of your OpenShift cluster."
}

variable "poolsize" {
    default = "2"
    description = "Number of nodes in your cluster."
}

variable "service_name" {
    default = "composeformysql"
    description = "Service ID for the Compose for MySQL instance."
 }

 variable "kube_version" {
    default = "3.11_openshift"
    description = "Version of OpenShift."
 }
 variable "hardware" {
    default = "shared"
    description = "Type of hardware for OpenShift cluster."
 }
