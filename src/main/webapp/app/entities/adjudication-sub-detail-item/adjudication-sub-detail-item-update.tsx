import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAdjudicationDetailItem } from 'app/shared/model/adjudication-detail-item.model';
import { getEntities as getAdjudicationDetailItems } from 'app/entities/adjudication-detail-item/adjudication-detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication-sub-detail-item.reducer';
import { IAdjudicationSubDetailItem } from 'app/shared/model/adjudication-sub-detail-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdjudicationSubDetailItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjudicationSubDetailItemUpdate = (props: IAdjudicationSubDetailItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { adjudicationSubDetailItemEntity, adjudicationDetailItems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/adjudication-sub-detail-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAdjudicationDetailItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...adjudicationSubDetailItemEntity,
        ...values,
        adjudicationDetailItem: adjudicationDetailItems.find(it => it.id.toString() === values.adjudicationDetailItemId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.adjudicationSubDetailItem.home.createOrEditLabel"
            data-cy="AdjudicationSubDetailItemCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailItem.home.createOrEditLabel">
              Create or edit a AdjudicationSubDetailItem
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : adjudicationSubDetailItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="adjudication-sub-detail-item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="adjudication-sub-detail-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sequenceLabel" for="adjudication-sub-detail-item-sequence">
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailItem.sequence">Sequence</Translate>
                </Label>
                <AvField
                  id="adjudication-sub-detail-item-sequence"
                  data-cy="sequence"
                  type="string"
                  className="form-control"
                  name="sequence"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="adjudication-sub-detail-item-adjudicationDetailItem">
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailItem.adjudicationDetailItem">
                    Adjudication Detail Item
                  </Translate>
                </Label>
                <AvInput
                  id="adjudication-sub-detail-item-adjudicationDetailItem"
                  data-cy="adjudicationDetailItem"
                  type="select"
                  className="form-control"
                  name="adjudicationDetailItemId"
                >
                  <option value="" key="0" />
                  {adjudicationDetailItems
                    ? adjudicationDetailItems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/adjudication-sub-detail-item" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  adjudicationDetailItems: storeState.adjudicationDetailItem.entities,
  adjudicationSubDetailItemEntity: storeState.adjudicationSubDetailItem.entity,
  loading: storeState.adjudicationSubDetailItem.loading,
  updating: storeState.adjudicationSubDetailItem.updating,
  updateSuccess: storeState.adjudicationSubDetailItem.updateSuccess,
});

const mapDispatchToProps = {
  getAdjudicationDetailItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjudicationSubDetailItemUpdate);
