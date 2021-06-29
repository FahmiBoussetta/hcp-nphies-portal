import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAdjudicationDetailItem } from 'app/shared/model/adjudication-detail-item.model';
import { getEntities as getAdjudicationDetailItems } from 'app/entities/adjudication-detail-item/adjudication-detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication-detail-notes.reducer';
import { IAdjudicationDetailNotes } from 'app/shared/model/adjudication-detail-notes.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationDetailNotesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adjudicationDetailItems = useAppSelector(state => state.adjudicationDetailItem.entities);
  const adjudicationDetailNotesEntity = useAppSelector(state => state.adjudicationDetailNotes.entity);
  const loading = useAppSelector(state => state.adjudicationDetailNotes.loading);
  const updating = useAppSelector(state => state.adjudicationDetailNotes.updating);
  const updateSuccess = useAppSelector(state => state.adjudicationDetailNotes.updateSuccess);

  const handleClose = () => {
    props.history.push('/adjudication-detail-notes');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdjudicationDetailItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...adjudicationDetailNotesEntity,
      ...values,
      adjudicationDetailItem: adjudicationDetailItems.find(it => it.id.toString() === values.adjudicationDetailItemId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...adjudicationDetailNotesEntity,
          adjudicationDetailItemId: adjudicationDetailNotesEntity?.adjudicationDetailItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.adjudicationDetailNotes.home.createOrEditLabel" data-cy="AdjudicationDetailNotesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.adjudicationDetailNotes.home.createOrEditLabel">
              Create or edit a AdjudicationDetailNotes
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="adjudication-detail-notes-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.adjudicationDetailNotes.note')}
                id="adjudication-detail-notes-note"
                name="note"
                data-cy="note"
                type="text"
              />
              <ValidatedField
                id="adjudication-detail-notes-adjudicationDetailItem"
                name="adjudicationDetailItemId"
                data-cy="adjudicationDetailItem"
                label={translate('hcpNphiesPortalApp.adjudicationDetailNotes.adjudicationDetailItem')}
                type="select"
              >
                <option value="" key="0" />
                {adjudicationDetailItems
                  ? adjudicationDetailItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/adjudication-detail-notes" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AdjudicationDetailNotesUpdate;
