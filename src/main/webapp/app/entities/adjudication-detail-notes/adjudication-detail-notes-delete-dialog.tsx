import React, { useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './adjudication-detail-notes.reducer';

export const AdjudicationDetailNotesDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const adjudicationDetailNotesEntity = useAppSelector(state => state.adjudicationDetailNotes.entity);
  const updateSuccess = useAppSelector(state => state.adjudicationDetailNotes.updateSuccess);

  const handleClose = () => {
    props.history.push('/adjudication-detail-notes');
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(adjudicationDetailNotesEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="adjudicationDetailNotesDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="hcpNphiesPortalApp.adjudicationDetailNotes.delete.question">
        <Translate
          contentKey="hcpNphiesPortalApp.adjudicationDetailNotes.delete.question"
          interpolate={{ id: adjudicationDetailNotesEntity.id }}
        >
          Are you sure you want to delete this AdjudicationDetailNotes?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-adjudicationDetailNotes" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default AdjudicationDetailNotesDeleteDialog;
