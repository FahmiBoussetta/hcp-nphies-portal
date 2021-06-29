import React, { useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './list-eligibility-purpose-enum.reducer';

export const ListEligibilityPurposeEnumDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const listEligibilityPurposeEnumEntity = useAppSelector(state => state.listEligibilityPurposeEnum.entity);
  const updateSuccess = useAppSelector(state => state.listEligibilityPurposeEnum.updateSuccess);

  const handleClose = () => {
    props.history.push('/list-eligibility-purpose-enum');
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(listEligibilityPurposeEnumEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="listEligibilityPurposeEnumDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="hcpNphiesPortalApp.listEligibilityPurposeEnum.delete.question">
        <Translate
          contentKey="hcpNphiesPortalApp.listEligibilityPurposeEnum.delete.question"
          interpolate={{ id: listEligibilityPurposeEnumEntity.id }}
        >
          Are you sure you want to delete this ListEligibilityPurposeEnum?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-listEligibilityPurposeEnum"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default ListEligibilityPurposeEnumDeleteDialog;
