import Input from '../../../../components/ui/Input';


const VendorFields = ({ onDataChange }) => (
  <>
    <Input label="Shop Name" name="shopName" placeholder="Healthy Bites" onChange={onDataChange} required />
    <Input label="GST Number" name="gstNumber" placeholder="22AAAAA0000A1Z5" onChange={onDataChange} required />
    <Input label="Contact Number" name="contactNumber" placeholder="+91 9876543210" onChange={onDataChange} required />
  </>
);
export default VendorFields;