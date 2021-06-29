import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAdjudicationSubDetailItem } from 'app/shared/model/adjudication-sub-detail-item.model';
import { getEntities as getAdjudicationSubDetailItems } from 'app/entities/adjudication-sub-detail-item/adjudication-sub-detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication-sub-detail-notes.reducer';
import { IAdjudicationSubDetailNotes } from 'app/shared/model/adjudication-sub-detail-notes.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationSubDetailNotesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adjudicationSubDetailItems = useAppSelector(state => state.adjudicationSubDetailItem.entities);
  const adjudicationSubDetailNotesEntity = useAppSelector(state => state.adjudicationSubDetailNotes.entity);
  const loading = useAppSelector(state => state.adjudicationSubDetailNotes.loading);
  const updating = useAppSelector(state => state.adjudicationSubDetailNotes.updating);
  const updateSuccess = useAppSelector(state => state.adjudicationSubDetailNotes.updateSuccess);

  const handleClose = () => {
    props.history.push('/adjudication-sub-detail-notes');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdjudicationSubDetailItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...adjudicationSubDetailNotesEntity,
      ...values,
      adjudicationSubDetailItem: adjudicationSubDetailItems.find(it => it.id.toString() === values.adjudicationSubDetailItemId.toString()),
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
          ...adjudicationSubDetailNotesEntity,
          adjudicationSubDetailItemId: adjudicationSubDetailNotesEntity?.adjudicationSubDetailItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.adjudicationSubDetailNotes.home.createOrEditLabel"
            data-cy="AdjudicationSubDetailNotesCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.home.createOrEditLabel">
              Create or edit a AdjudicationSubDetailNotes
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
                  id="adjudication-sub-detail-notes-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.adjudicationSubDetailNotes.note')}
                id="adjudication-sub-detail-notes-note"
                name="note"
                data-cy="note"
                type="text"
              />
              <ValidatedField
                id="adjudication-sub-detail-notes-adjudicationSubDetailItem"
                name="adjudicationSubDetailItemId"
                data-cy="adjudicationSubDetailItem"
                label={translate('hcpNphiesPortalApp.adjudicationSubDetailNotes.adjudicationSubDetailItem')}
                type="select"
              >
                <option value="" key="0" />
                {adjudicationSubDetailItems
                  ? adjudicationSubDetailItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/adjudication-sub-detail-notes"
                replace
                color="info"
              >
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

export default AdjudicationSubDetailNotesUpdate;
