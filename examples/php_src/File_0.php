<?php
	class ConcreteClass extends AbstractClass{
		Type namespaceVariable;
		protected Type protectedVariable;
		// constructor
		public function ConcreteClass(){
			//TODO
		}
		public static function staticOperation($parameter){
			//TODO
		}
		// necessary overriden methods
		public function abstractOperation(){
			//TODO
			return null;
		}
		public function interfaceOperation(){
			//TODO
		}
	}
	abstract class AbstractClass implements Interface{
		private Type privateVariable;
		public OtherType publicVariable;
		public function publicOperation($parameter){
			//TODO
		}
		private function privateOperation(){
			//TODO
		}
		protected function protectedOperation(){
			//TODO
		}
		function namespaceOperation(){
			//TODO
		}
		abstract public function abstractOperation();
	}
	interface Interface{
		public function interfaceOperation();
	}
?>