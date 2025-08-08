# block
source "amazon-ebs" "example" {

  # argument
  ami_name = "abc123"
}

# this is some docs
variable "long_key" {
    type = "string"
    default = <<EOF
This is a long key.
Running over several lines.
It could be super handy for a boot_command.
EOF
}
